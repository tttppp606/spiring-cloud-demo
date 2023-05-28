package org.example.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义过滤器
 *
 * 自定义过滤器让全局都使用，只需要将自定义的过滤器由实现GatewayFilter接口，变为实现GlobalFilter接口
 */
@Component
@Slf4j
public class TimerFilter implements GatewayFilter, Ordered {
//public class TimerFilter implements GlobalFilter, Ordered {

        @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        timer.start(exchange.getRequest().getURI().getRawPath());
        //函数式编程回调函数 待研究 todo
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    timer.stop();
                    log.info(timer.prettyPrint());
                })
        );
    }

    /**
     * 返回值越小，表明该类的执行顺序越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
