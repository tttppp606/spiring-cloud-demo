package org.example.springcloud.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Created by 半仙.
 */
@Data
/**
 * 可以利用建造者模式构建对象
 *             Product prod = Product.builder()
 *                     .productId(pid)
 *                     .description("好吃不贵")
 *                     .stock(100L)
 *                     .build();
 */
@Builder
public class Product {

    private Long productId;

    private String description;

    private Long stock;//库存数量

}
