package org.example.springcloud.service;

import org.example.springcloud.entity.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("auth-service")
@RequestMapping("auth")
public interface AuthService {
    @PostMapping("/login")
    @ResponseBody
    public AuthResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password);

    @GetMapping("/verify")
    public AuthResponse verify(@RequestParam("token") String token,
                               @RequestParam("username") String username);

    @PostMapping("/refresh")
    @ResponseBody
    public AuthResponse refresh(@RequestParam("refresh") String refresh);
}
