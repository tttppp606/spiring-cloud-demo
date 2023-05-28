package org.example.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springcloud.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于仿制秒杀场景，利用网管层Gateway实现
 *
 * Product类放在了接口模块feign-client-intf里，也是微服务的常用写法
 */
@RestController
@Slf4j
@RequestMapping("gateway")
public class GatewayController {

    public static final Map<Long, Product> items = new ConcurrentHashMap<>();
    @GetMapping(value = "details")
    @ResponseBody
    public Product get(@RequestParam("pid") Long pid) {
        if (!items.containsKey(pid)) {
            Product prod = Product.builder()
                    .productId(pid)
                    .description("好吃不贵")
                    .stock(100L)
                    .build();
            items.putIfAbsent(pid, prod);
        }
        return items.get(pid);
    }

    @PostMapping(value = "placeOrder")
    public String buy(@RequestParam("pid") Long pid) {
        Product prod = items.get(pid);

        if (prod == null) {
            return "Product not found";
        } else if (prod.getStock() <= 0L) {
            return "Sold out";
        }

        synchronized (prod) {
            if (prod.getStock() <= 0L) {
                return "Sold out";
            }
            prod.setStock(prod.getStock() - 1);
        }

        return "Order Placed";
    }

}
