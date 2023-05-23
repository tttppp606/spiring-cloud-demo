package org.example.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope   //该类可以动态读取配置中心的参数
public class Controller {

    /**
     * 直接从配置中心读取name
     */
    @Value("${name}")
    private String name;

    /**
     * 先读取到本地配置文件myWords，再映射到words
     */
    @Value("${myWords}")
    private String words;

    @GetMapping("/name")
    public String getName(){
        return name;
    }

    @GetMapping("/words")
    public String getWords(){
        return words;
    }

}
