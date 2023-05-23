package org.example.springcloud.service;

import org.example.springcloud.entity.Friend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 作用：
 * 利用feign，会在服务调用者代码里增加一个IService接口，必须将目标服务节点的serverNamne和path重新配置一次，
 * 相当于同一个配置在服务节点和调用方各写了一次，所以此模块就是把这些配置提取到一个公共模块中，
 * 服务方的controller继承该接口，调用方@Autowired该接口，实现同一个配置只写一次
 *
 * 注意：
 * 其他项目继承该类的，会导致那些不用feign/springcloud的项目也引入了feign
 * 所以一般在项目打包的时候，都会分别打两个包，一个非springcloud包和一个springcloud包
 */
@FeignClient("feign-client")
public interface IService {
    @GetMapping("/sayHi")
    public String sayHi();

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend);

    @GetMapping("/retry")
    public String retry(@RequestParam("timeout") int timeout);

    @GetMapping("/error")
    public String   error();
}
