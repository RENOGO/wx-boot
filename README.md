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
│   ├── wx-common-log #log相关工具包
│   ├── wx-common-mybatis-plus #mybatis-plus相关工具包
│   └── wx-common-security #安全模块相关工具
├── wx-gateway  #网关，待实现
├── wx-demo #演示工程
└── wx-services #服务
    ├── wx-auth #鉴权服务，待实现
    └── wx-usercenter #用户中心，待实现
```

# 四、功能说明

​	这一节我将介绍wx-boot协助开发者快速开发的一系列脚手架功能，作者认为一个好用的工具，应该还有完备的使用文档，由于该项目刚开始搭建，所以需要时间来完善文档和功能；本节介绍的功能集中在common包下，目前作者优先提供这部分功能，后续其他预期功能在建设中。

## 响应体

统一的响应体处理，无需再拼接统一响应体

```
@RequestMapping("/commonRes")
@RestController
public class ResController {

    @GetMapping("/test1")
    public Map<String, Object> test1() {
        Map<String, Object> res = new HashMap<>();
        res.put("key", "value");
        return res;
    }

}
```

响应内容

```
{
  "code": 200,
  "message": "success",
  //所有内容均统一封装在这里
  "data": {
    "key": "value"
  },
  "sysMessage": null
}
```

application配置文件

```
wx-boot:
   web:
     #controller路径
     base-controller-path: com.wx.demo1.controller
     #开发测试环境下返回错误信息，下面的异常处理会说明这个配置的含义
     show-sys-error: true
```

## 异常处理

统一的异常处理

```
@RequestMapping("/commonRes")
@RestController
public class ResController {

    @GetMapping("/test2")
    public Map<String, Object> test2() {
        if (true) {
            //模拟业务错误需要抛出异常，以返回错误信息，下面的枚举后续文档会进行说明
            throw new BusinessException(ResponseEnum.MISSING_PARAMETERS);
        }
        return null;
    }

    @GetMapping("/test3")
    public Map<String, Object> test3() {
        //模拟异常代码
        int exception = 1 / 0;
        return null;
    }

}

```

响应内容

```
test2接口
{
  "code": 4001,
  "message": "缺少参数",
  "data": null,
  "sysMessage": null
}
test3接口
{
  "code": 400,
  "message": "错误请求!",
  "data": null,
  //这里由上一小节提到的show-sys-error字段配置实现的，在开发和测试环境中可配置开启该字段，以快速排查问题
  //如果出现类似空指针的情况，可以快速的从这里找到问题
  "sysMessage": "ArithmeticException: / by zero"
}
```

以下内容待文档补充，大部分均

swagger配置

日志打印

延时双删

分布式锁

主从选举

消息可靠性

统一数据实体

...

# 五、快速开始

Todo

# 六、部署教程

Todo

# 七、说明

1、wx-boot 是一个开源项目，托管在 GitHub 上，你可以自由访问和使用它。它拥有一个活跃的开发者社区，不断更新和维护，你可以通过提交问题和贡献代码来参与其中。立即访问 wx-boot 的 GitHub 项目链接，开始使用这个强大的微服务脚手架，加速你的微服务开发和部署。

2、wx-boot 目前只有后台实现，前端可视化界面等仍需要作者找时间去完善开发。

# 八、todo-list

1、鉴权网关、鉴权中心、用户中心

2、ELK、SkyWalking、Prometheus、Arthas、K8S等外部部署实现说明

3、前端可视化实现

# 九、常见问题