eureka-client:客户端，当作后台服务节点

eureka-consumer：消费端，当作客户，调用eureka-client

eureka-peer1和eureka-peer2：双注册中心，高可用

eureka-server：单注册中心

ribbon-consumer：ribbon和eureka结合，并调用eureka-client

feign-client 将controller层抽象到feign-client-inf模块

feign-consumer 消费方利用feign的重写Iservice接口进行服务调用eureka-client

feign-consumer-advanced  消费方引入服务方的抽象层feign-client-inf模块进行对feign-client的服务调用


