package org.example.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private String username;

    private String token;

    //当token快过期的时候，可以通过refreshToken生成一个新的token
    private String refreshToken;
}
