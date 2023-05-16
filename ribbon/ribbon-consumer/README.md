改变Ribbon负载均衡策略的三种方式：
+ 配置文件
指定来对eureka-client客户端的负载均衡，下面两种方法是全局改变
````
# 改变ribbon的负载均衡策略
eureka-client:
ribbon:
NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule
````
+ 配置类的注解
````
  @RibbonClient(name = "eureka-client",configuration = com.netflix.loadbalancer.RandomRule.class)
````
+ 配置类的代码
````
 @Bean
 public IRule defaultLBStrategy(){ 
      return new RandomRule();
 }
````