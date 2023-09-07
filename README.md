# wx-boot

# 一、介绍

wx-boot 是一个专为 Java 开发者设计的快速搭建微服务的脚手架，旨在帮助开发者快速构建高效、可靠的微服务架构，无论你是初学者还是经验丰富的开发者，跟着文档说明走，都能够快速上手即用！

wx-boot集成了多个关键组件和技术，包括 Spring Gateway、ZooKeeper、Dubbo、Redisson 和 Hystrix等，同时提供了 Docker 和 Kubernetes (K8s) 部署支持。

## 不是纯粹的集成库

wx-boot不是纯粹的框架集成库，还提供了多维度的鉴权管理、用户管理、日志采集等功能，以及许多便捷的开发辅助封装，帮助开发者快速搭建自己的服务，如kafka、rabitmq的可靠性处理约束、延时双删、分布式锁接入、主从节点选举、请求响应体规范、异常处理、日志处理、实体约束等一系列辅助功能，下面都会一一介绍。

## 不仅仅是一个脚手架

wx-boot不仅仅是一个脚手架工程，也是一个完整的微服务构建部署方案，并提供了相关组件部署开发说明，尽量减少开发者的上手难度。

1、针对zookeeper、nginx、mysql、redis等需要额外安装配置的中间件，也提供了对应的docker-compose实现，后续也会补充k8s的实现。对docker、docker-compose、k8s等都不熟悉？没关系，在这个项目后续也会有教程一一教会你如何部署使用。

2、对于ELK、SkyWalking、Prometheus、Arthas等同样有使用门口的组件，这里同样会提供相关的部署开发教程。

# 二、架构图

注意，并不是要求所有开发者都按照如下架构进行搭建，如ELK日志采集、链路追踪、监控等都是不是必须的，开发者根据自身情况选配，这个架构图仅仅提供了作者对微服务整套体系搭建的一个思路。

![img](https://cdn.nlark.com/yuque/0/2023/png/22795528/1694064153466-de4da45e-6637-4d04-8677-628c3e1d19fb.png)

# 三、工程结构

目前项目仍在完善，这是部分已实现和待实现的结构目录

```
.
├── wx-common #通用包
│   ├── wx-common-core #核心工具包	
│   ├── wx-common-lock #分布式锁
│   │   └── wx-common-lock-redis #分布式锁,redisson实现
│   ├── wx-common-master #主从节点选举
│   │   └── wx-common-master-redis #redis实现主从选举
│   ├── wx-common-redis #redis工具包
│   ├── wx-common-swagger #swagger工具包
│   ├── wx-common-web #web相关工具包
│   ├── wx-mybatis-plus #mybatis-plus相关工具包
│   └── wx-security #安全模块相关工具
├── wx-gateway  #网关，待实现
├── wx-demo #演示工程
└── wx-services #服务
    ├── wx-auth #鉴权服务，待实现
    └── wx-usercenter #用户中心，待实现
```

# 四、功能说明

以下功能集中在common包下，目前作者优先提供这部分功能，后续其他预期功能在建设中

swagger配置

响应体配置

异常返回

延时双删

分布式锁

主从选举

消息可靠性

统一数据实体

...

# 五、快速开始

# 六、部署教程

Todo

# 七、说明

1、wx-boot 是一个开源项目，托管在 GitHub 上，你可以自由访问和使用它。它拥有一个活跃的开发者社区，不断更新和维护，你可以通过提交问题和贡献代码来参与其中。立即访问 wx-boot 的 GitHub 项目链接，开始使用这个强大的微服务脚手架，加速你的微服务开发和部署。

2、wx-boot 目前只有后台实现，前端可视化界面等仍需要作者找时间去完善开发。

# 八、todo-list

1、鉴权网关、鉴权中心、用户中心

2、

3、ELK、SkyWalking、Prometheus、Arthas、K8S等外部部署实现说明

4、前端可视化实现

# 九、常见问题