package com.asoulfan.platform.user.common;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import com.asoulfan.platform.user.dto.UserDto;
import com.google.gson.Gson;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;

import cn.hutool.json.JSONUtil;

/**
 * @author asuka
 * @date 2021/11/07
 */
@Component
public class JwtComponent {
    private static final Logger log = LoggerFactory.getLogger(JwtComponent.class);

    private RSAPublicKey publicKey;

    private PrivateKey privateKey;

    @PostConstruct
    public void init() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("asf-jwt.jks"), "xiangwan1024".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("asf-jwt", "xiangwan1024".toCharArray());
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public String generateJwt(Object payloadObject) {
        String keyId = RandomUtils.nextLong() + "";

        //生成id_token
        JWSSigner jwsSigner = new RSASSASigner(privateKey, true);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(keyId).build();

        final String payloadText = new Gson().toJson(payloadObject);
        Payload payload = new Payload(payloadText);
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (Exception e) {
            log.error("create jwt token error", e);
            throw new RuntimeException(e);
        }
    }

    public UserDto verify(String jwt) {
        try {
            //从token中解析JWS对象
            JWSObject jwsObject = JWSObject.parse(jwt);
            //使用RSA公钥创建RSA验证器
            JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);
            if (!jwsObject.verify(jwsVerifier)) {
                throw new RuntimeException("token签名不合法！");
            }
            String payload = jwsObject.getPayload().toString();

            return JSONUtil.toBean(payload, UserDto.class);
        } catch (Exception e) {
            log.error("verify error", e);
            return null;
        }

    }
}
