package com.asoulfan.platform.common.nacos;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.google.gson.Gson;

/**
 * @author asuka
 * @date 2021/11/06
 */
@Component
public class NacosValueRegister extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, BeanFactoryAware {
    private static final Logger log = LoggerFactory.getLogger(NacosValueRegister.class);

    private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>();

    private final Map<Object, Field> registeredFieldsMap = new HashMap<>();

    private TypeConverter typeConverter;

    @Autowired
    private NacosConfigManager nacos;

    private final Gson gson = new Gson();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        this.typeConverter = configurableBeanFactory.getTypeConverter();
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        InjectionMetadata metadata = findNacosValueMetadata(beanName, beanType, null);
        metadata.checkConfigMembers(beanDefinition);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = findNacosValueMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException e) {
            throw e;
        } catch (Throwable throwable) {
            throw new BeanCreationException(beanName, "Failed to inject nacos value", throwable);
        }
        return pvs;
    }

    public InjectionMetadata findNacosValueMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        String cacheKey = StringUtils.isEmpty(beanName) ? clazz.getName() : beanName;
        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildNacosValueMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError ex) {
                        throw new IllegalStateException("Invalid @NacosValue declaration on bean with name '" + beanName + "'", ex);
                    }
                }
            }
        }

        return metadata;
    }

    public InjectionMetadata buildNacosValueMetadata(Class<?> clazz) {
        List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do {
            List<InjectionMetadata.InjectedElement> currentElement = new LinkedList<>();
            ReflectionUtils.doWithLocalFields(targetClass, field -> {
                NacosValue nacosValue = AnnotationUtils.getAnnotation(field, NacosValue.class);
                if (nacosValue == null) {
                    return;
                }
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    log.warn("@NacosValue is not supported on static fields or final fields");
                    return;
                }
                currentElement.add(new InjectionMetadata.InjectedElement(field, null) {
                    @Override
                    protected void inject(Object target, String requestingBeanName, PropertyValues pvs) throws Throwable {
                        if (registeredFieldsMap.get(target) != null && registeredFieldsMap.get(target) == field) {
                            return;
                        }
                        try {
                            Class<?> fieldType = field.getType();
                            String valueStr = nacos.getConfigService().getConfig(nacosValue.dataId(), nacosValue.group(),
                                    TimeUnit.SECONDS.toMillis(5));
                            if (StringUtils.isNotEmpty(valueStr)) {
                                fillData(valueStr, fieldType, field, target);
                            }

                            nacos.getConfigService().addListener(nacosValue.dataId(), nacosValue.group(), new AbstractListener() {
                                @Override
                                public void receiveConfigInfo(String configInfo) {
                                    fillData(configInfo, fieldType, field, target);
                                    log.info("[nacos change]group: {}, dataId: {}, change value to {}", nacosValue.group(), nacosValue.dataId(), configInfo);
                                }
                            });
                        } catch (Exception e) {
                            throw new BeanCreationException("Failed to autowire NacosValue property", e);
                        }

                        registeredFieldsMap.put(target, field);
                    }
                });
            });

            elements.addAll(0, currentElement);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    private void fillData(String configInfo, Class<?> fieldType, Field field, Object target) {
        Object value;
        if (StringUtils.isEmpty(configInfo) && fieldType.isPrimitive()) {
            value = getPrimitiveDefaultValue(fieldType);
        } else {
            if (BeanUtils.isSimpleProperty(fieldType)) {
                value = typeConverter.convertIfNecessary(configInfo, fieldType, field);
            } else {
                Type type = field.getGenericType();
                value = gson.fromJson(configInfo, type);
            }
        }
        try {
            FieldUtils.writeField(field, target, value, true);
        } catch (IllegalAccessException e) {
            log.error("nacos write field error, field: {}, value: {}", field, value, e);
        }
    }

    private Object getPrimitiveDefaultValue(Class<?> primitiveType) {
        if (primitiveType == int.class) {
            return 0;
        } else if (primitiveType == long.class) {
            return 0L;
        } else if (primitiveType == double.class) {
            return 0.0D;
        } else if (primitiveType == float.class) {
            return 0.0F;
        } else if (primitiveType == boolean.class) {
            return false;
        } else if (primitiveType == char.class) {
            return '\u0000';
        } else if (primitiveType == byte.class) {
            return (byte) 0;
        } else if (primitiveType == short.class) {
            return (short) 0;
        }
        return null;
    }
}
