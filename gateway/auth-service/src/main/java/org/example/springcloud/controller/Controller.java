package org.example.springcloud.controller;

import org.example.springcloud.entity.Account;
import org.example.springcloud.entity.AuthResponse;
import org.example.springcloud.service.AuthService;
import org.example.springcloud.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.example.springcloud.entity.AuthResponseCode.*;

/**
 * 登陆
 * 1、登陆的时候，验证用户名和密码一致，就会生成一个token
 * 2、将token和用户名封装为一个对象account作为value，再生成一个key-RefreshToken，放入redis中
 * 刷新
 * 1需要验证身份的时候，前端传过来RefreshToken，
 * 后端匹配到redis里的account后，跟登陆一样重新生成token，重新生成RefreshToken，
 * 存入redis里，返回前端新的RefreshToken
 *
 */
@RestController
public class Controller implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 1、利用account里的username生成对应的token
     * 2、将生成的token和username放入redis，
     *    其中key是随机生成的RefreshToken，value是username和token封装的对象account
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthResponse login(String username, String password) {
        Account account = Account.builder()
                .username(username)
                .build();

        // TODO 验证username + password

        //利用account里的username生成对应的token
        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        //将生成的token和username放入redis，其中key是随机生成的RefreshToken，value是username和token封装的对象account
        redisTemplate.opsForValue().set(account.getRefreshToken(), account);

        return AuthResponse.builder()
                .account(account)
                .code(SUCCESS)
                .build();
    }

    /**
     *
     * @param token
     * @param username
     * @return
     */
    @Override
    public AuthResponse verify(String token, String username) {
        boolean success = jwtService.verify(token, username);
        return AuthResponse.builder()
                // TODO 此处最好用invalid token之类的错误信息
                .code(success ? SUCCESS : INCORRECT_PWD)
                .build();
    }

    /**
     * 1、refreshToken当作key获取redis中的value
     * 2、如果不存在，返回失败，如果存在，继续3
     * 3、重新生成token，重新存入redis中、删除redis原来的token
     * @param refreshToken
     * @return
     */
    @Override
    public AuthResponse refresh(String refreshToken) {
        Account account = (Account)redisTemplate.opsForValue().get(refreshToken);
        if (account == null) {
            return AuthResponse.builder()
                    .code(USER_NOT_FOUND)
                    .build();
        }

        String jwt = jwtService.token(account);
        account.setToken(jwt);
        account.setRefreshToken(UUID.randomUUID().toString());

        redisTemplate.delete(refreshToken);
        redisTemplate.opsForValue().set(account.getRefreshToken(), account);

        return AuthResponse.builder()
                .account(account)
                .code(SUCCESS)
                .build();
    }
}
