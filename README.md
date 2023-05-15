eureka-client:客户端，当作后台服务节点

eureka-consumer：消费端，当作客户，调用eureka-client

eureka-peer1和eureka-peer2：双注册中心，高可用

eureka-server：单注册中心

ribbon-consumer：ribbon和eureka结合，并调用eureka-client
