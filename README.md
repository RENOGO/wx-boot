# wx-boot

# 一、介绍

wx-boot 是一个专为 Java 开发者设计的快速搭建微服务的脚手架，旨在帮助开发者快速构建高效、可靠的微服务架构，无论你是初学者还是经验丰富的开发者，跟着文档说明走，都能够快速上手即用！

wx-boot集成了多个关键组件和技术，包括 Spring Gateway、ZooKeeper、Dubbo、Redisson 和 Hystrix等，同时提供了 Docker 和 Kubernetes (K8s) 部署支持。

## 不是纯粹的集成库

wx-boot不是纯粹的框架集成库，还提供了多维度的鉴权管理、用户管理、日志采集等功能，以及许多便捷的开发辅助封装，帮助开发者快速搭建自己的服务，如kafka、rabitmq的可靠性处理约束、延时双删、分布式锁接入、主从节点选举、请求响应体规范、异常处理、日志处理、实体约束等一系列辅助功能，下面都会一一介绍。

## 不仅仅是一个脚手架

wx-boot不仅仅是一个脚手架工程，也是一个完整的微服务构建部署方案，并提供了相关组件部署开发说明，尽量减少开发者的上手难度。

1、针对zookeeper、nginx、mysql、redis等需要额外安装配置的中间件，也提供了对应的docker-compose实现，后续也会补充k8s的实现。对docker、docker-compose、k8s等都不熟悉？没关系，在这个项目后续也会有教程一一教会你如何部署使用。

2、对于ELK、SkyWalking、Prometheus、Arthas等同样有使用门口的组件，这里同样会提供相关的部署开发教程，帮助开发者快速搭建自己的服务生态。

# 二、架构图

注意，并不是要求所有开发者都按照如下架构进行搭建，如ELK日志采集、链路追踪、监控等都是不是必须的，开发者根据自身情况选配，这个架构图仅仅提供了作者对微服务整套体系搭建的一个思路。

![img](./assets/架构图.png)

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
│   │		└── wx-common-master-starter-redis #redis实现主从选举starter
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

​	这一节我将介绍wx-boot协助开发者快速开发的一系列脚手架功能，作者认为一个好用的工具，应该还有完备的使用文档，由于该项目刚开始搭建，所以需要时间来完善文档和功能；本节介绍的功能集中在common包下，目前作者优先提供这部分功能，后续其他预期功能任在开发建设中。

## 响应体

统一的响应体处理，无需再拼接统一响应体

```
@RequestMapping("/commonRes")
@RestController
public class ResController {

		//这里的返回可以是任意数据实体，不局限于Map类
    @GetMapping("/test1")
    public Map<String, Object> test1() {
        Map<String, Object> res = new HashMap<>();
        res.put("key", "value");
        return res;
    }
    
    //任意实体
    @GetMapping("/testUser")
    public User testUser() {
        User user = new User();
        user.setName("Jack");
        user.setSex(1);
        return user;
    }

		//字符串
    @GetMapping("/testStr")
    public String testStr() {
        return "this is a string";
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
  //这个字段下一节会介绍
  "sysMessage": null
}

{
  "code": 200,
  "message": "success",
  //所有内容均统一封装在这里
  "data": {
    "name": "jack",
    "sex": 1
  },
  "sysMessage": null
}

{
  "code": 200,
  "message": "success",
  //所有内容均统一封装在这里
  "data": "this is a string",
  "sysMessage": null
}
```

application配置文件

```
wx-boot:
   web:
     #controller路径，多个用,号隔开
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

## swagger配置

application配置，完成如下内容配置，即可使用swagger

```
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

wx-boot:
   swagger:
     //配置是否开启，生产环境下可选择关闭
     enabled: true
     basePackage: com.wx.demo1.controller
     title: 文档标题
     description: 文档描述
     version: 1.0.0
     headers:
       - headerName: 自定义请求头1
         description: 自定义请求头描述1
         type: String
       - headerName: 自定义请求头2
         description: 自定义请求头描述2
         type: String
```

controller配置，这个和平常配置swagger的步骤是一样的，没有任何修改

```
@RequestMapping("/commonRes")
@RestController
@Api(tags = "测试控制器")
public class ResController {


    @GetMapping("/test1")
    @ApiOperation("测试响应接口1")
    public Map<String, Object> test1() {
        Map<String, Object> res = new HashMap<>();
        res.put("key", "value");
        return res;
    }

```

访问`项目路径/swagger-ui.html`即可开始使用swagger了

![iShot_2023-09-12_12.38.56](assets/iShot_2023-09-12_12.38.56.png)

## 主从选举和心跳管理

**为什么服务也要有主从选举呢？**

在实际生产开发中，基于如下几个场景可能会使用到主从选举：

1. 数据预热，需要选举一个服务来完成这项工作（当然定时任务等其他方式也可以实现，这里只是提供一种实现思路，下面的同理）
2. 任务分发，任务服务组内的服务需要做任务调度分发，可从任务服务组内选举出一个主节点来实现
3. 心跳监控，如果不期望单独开设一个服务来监控心跳提高维护成本，可在服务集群中选举一个做心跳监控
4. 等等很多场景这里不列举了...

**为什么要基于redis实现？**

使用zk同样可以实现选举，这里提供redis是给不期望引入zk的开发者提供的，后续我也会补充一个基于zk实现的主从选举。

**如何使用？**

application配置

```
wx-boot:
  master:
    redis:
      port: ${server.port}
      #是否发送心跳，当true的时候，所有节点可以通过api拿到当前存活的节点信息
      sendHeartBeat: true
      #节点的额外信息，可选内容，可不写
      extra:
        key1: value1
        key2: value2
#      unique: 节点唯一标识，可不填，默认是host:port
#      host: 节点host，可不填
```

Master监听

```
@Component
@Slf4j
public class MasterListener implements MasterChangeListener {

    /**
     * 当master发生变化的时候，会通过该方法通知
     *
     * @param isMaster   当前节点是否是master
     * @param masterInfo master的信息
     */
    @Override
    public void change(boolean isMaster, NodeInfo masterInfo) {
        log.info("当前节点是否抢到了master节点 - {}", isMaster);
        log.info("master节点信息{}", JSONUtil.toJsonStr(masterInfo));
    }
}

```

存活节点信息获取

```
    public void heart() {
        //获取存活的节点信息
        Map<String, NodeInfo> aliveNode = NodeManager.getInstance().getAliveNode();
    }
```

## 分布式锁

目前的分布式锁脚手架是基于redisson实现的，使用方式如下：

```
@Service
public class LockService {


    /**
     * 固定锁名称的分布式锁
     */
    @RedisLock(lockNamePre = "wx_boot", lockName = "lock1")
    public void lock1() {
        //...业务代码
    }


    /**
     * 锁是基本数据类型或包装类
     */
    @RedisLock(lockNamePre = "wx_boot")
    public void lock2(@RedisLockName String lockName) {
        //...业务代码
    }


    /**
     * 锁是实体类的属性，RedisLockName#name声明该对象的名称
     */
    @RedisLock(lockNamePre = "wx_boot")
    public void lock3(@RedisLockName(name = "sex") User user) {
        //...业务代码
    }

    /**
     * 使用trylock,抢不到锁就返回
     */
    @RedisLock(lockNamePre = "wx_boot", lockName = "lock1", tryLock = true)
    public void lock4() {
        //...业务代码
    }

    /**
     * 使用trylock,等待1s抢不到锁就返回
     */
    @RedisLock(lockNamePre = "wx_boot", lockName = "lock1", tryLock = true, leaseTime = 1000)
    public void lock5() {
        //...业务代码
    }
    
    //...还有很多用法组合，具体看RedisLock注解
}

```

如果不期望使用注解的方式实现分布式锁，这里也提供了一个`RedissonLockUtil`的工具类给开发者使用。

该模块现有功能已经能满足业务开发使用了，还有很多想实现的功能优化点，后续也会慢慢更新。

## 待文档补充

日志打印

延时双删

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

1. 鉴权网关、鉴权中心、用户中心等服务的建设
1. 继续完善脚手架相关功能及说明文档
1. zk、ELK、SkyWalking、Prometheus、Arthas、K8S等外部部署实现说明
1. 前端可视化实现

# 九、常见问题