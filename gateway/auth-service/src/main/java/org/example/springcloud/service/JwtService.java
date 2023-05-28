package org.example.springcloud.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.ECDSAKeyProvider;
import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.entity.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    /**
     * 有了key可以生成正确的token，生产中，都是外部传进来，开发人员并不知道
     */
    private final static String KEY = "auth";
    private final static Long TOKEN_EXP_TIME = 60000L;
    private final static String USER_NAME = "auth";
    private final static String ISSUER = "lichuang";



    public String token(Account acct){
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        String token = JWT.create().withClaim(USER_NAME, acct.getUsername()) // 自定义的东西，token中带有username，验证时也必须验证
                .withExpiresAt(new Date(new Date().getTime() + TOKEN_EXP_TIME)) // token的过期时间
                .withIssuer(ISSUER) //token中带有ISSUER，验证时也必须验证，增加类安全性
                .withIssuedAt(new Date())
                .sign(algorithm); //生成token的算法

        log.info("jwt generated user={}", acct.getUsername());

        return token;
    }

    public boolean verify(String token,String username){
        Algorithm algorithm = Algorithm.HMAC256(KEY);

        try {
            //用与生成token一样的ISSUER、Claim和算法生成验证器
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .withClaim(USER_NAME, username)
                    .build();

            verifier.verify(token); //如果token过期或者不正确，就会抛出异常
            return true;
        } catch (Exception e){
            log.error("auth failed", e);
            return false;
        }
    }

}
