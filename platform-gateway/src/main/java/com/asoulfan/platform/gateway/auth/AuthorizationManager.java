package com.asoulfan.platform.gateway.auth;

import java.net.URI;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.asoulfan.platform.gateway.config.IgnoreUrlsConfig;
import com.asoulfan.platform.common.constant.AuthConstant;

import reactor.core.publisher.Mono;

/**
 * 授权管理
 *
 * @author Cscar
 * @since 2021-07-26 18:02
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //非管理端路径直接放行
        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // FIXME 2021/11/7
        // //不同用户体系登录不允许互相访问
        // try {
        //     String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        //     if (StrUtil.isEmpty(token)) {
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        //     String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        //     JWSObject jwsObject = JWSObject.parse(realToken);
        //     String userStr = jwsObject.getPayload().toString();
        //     UserJwtDto userDto = JSONUtil.toBean(userStr, UserJwtDto.class);
        //     //FIXME：
        //     // fengling：
        //     // auth模块里初始化oauth2时指定了clientId为admin-app,导致user模块请求token只能设置cilentId=admin-app
        //     // 此处先设置clientId=admin-app可以通过访问/asfbbs-admin 和/asfbbs-user 模块下的接口，后续建议优化
        //     if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId())
        //             && (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) && !pathMatcher.match(AuthConstant.User_URL_PATTERN, uri.getPath())) {
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        //     if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
        //         return Mono.just(new AuthorizationDecision(false));
        //     }
        // } catch (ParseException e) {
        //     e.printStackTrace();
        //     return Mono.just(new AuthorizationDecision(false));
        // }

        //管理端路径需校验权限
        // Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        // Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        // List<String> authorities = new ArrayList<>();
        // while (iterator.hasNext()) {
        //     String pattern = (String) iterator.next();
        //     if (pathMatcher.match(pattern, uri.getPath())) {
        //         authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
        //     }
        // }
        //
        // // 认证通过且角色匹配的用户可访问当前路径
        // // todo 需要判断角色是否匹配
        // if (authorities.size() > 0) {
        //     return Mono.just(new AuthorizationDecision(true));
        // }
        // return mono
        //         .filter(Authentication::isAuthenticated)
        //         .flatMapIterable(Authentication::getAuthorities)
        //         .map(GrantedAuthority::getAuthority)
        //         .any(authorities::contains)
        //         .map(AuthorizationDecision::new)
        //         .defaultIfEmpty(new AuthorizationDecision(false));


        return Mono.just(new AuthorizationDecision(true));
    }

}
