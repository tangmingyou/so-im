

### 开发计划

- (05-08~05-11)router 一致性 hash，新增、删除、备份处理
  - 数据同步删除后，保留id一段时间，有请求进行重定向/转发
  - 新增：重算hash，发起数据同步，接收其他节点推送数据及数据更改日志，注册服务，其他节点删除数据
  - 正常删除节点：重算 hash，数据和更改日志推送给其他节点，取消注册，关闭服务
  - 宕机：备份服务接收全量同步数据和更改日志，服务探测到主服务不可用，注册到注册中心提供服务
- (05-12~05-12) 备用计划：router 层 id 号段负载均衡
- (05-13~05-14) dubbo 服务异步处理
- 功能开发：
  - (05-13~05-13) 用户注册功能
  - (05-14~05-14) 好友列表(在线状态:批量uid一致性hash, router查询)
    - 用户查询
    - 添加好友
    - 好友在线状态列表
  - (05-15~05-15) 消息群发(im-router 群消息路由,批量uid一致性哈希路由)
    - 创群：
    - 加群：发送申请，推送申请，推送回复
    - 删群
    - 群列表
  - (05-16~05-16) 聊天记录查询
- (05-17~05-18) das 部分接口消息队列异步写
- (05-19~05-20) 集群部署：docker swarm
- 监控：log4j2完善 + prometheus + grafana + loki + arthas
  - (05-21~05-21) prometheus 服务发现，exporter(服务发现) 开发
  - (05-22~05-22) 容器监控，主机监控，数据库监控，日志收集
  - (05-23~05-24) 应用监控
    - 通讯指标监控，请求量，吞吐量，延时，请求节点分布，链路追踪
    - 数据库连接池监控
- (05-25~05-25) 压测：压测开发
- (05-26~05-26) client:  控制台完善，grallvm 打包
- 后续：
  - 通讯加密
  - websocket 网关
  - web 页面开发
  - 异/同设备，多地登录


### 资料
一致性hash算法能否解决数据迁移的问题？
https://www.zhihu.com/question/521159623


### TODO

- router -> entry 负载均衡
- router 新增节点顺时针相邻节点数据一致性哈希迁移
- router 冗余节点存储数据不提供服务
- dubbo 服务异步处理提升吞吐量
- das 消息队列异步写
- entry 监控，http 查询 entry 地址返回接口
- 功能开发：
    - 消息群发(im-router 群消息路由,批量uid一致性哈希路由)
    - 好友列表(在线状态:批量uid一致性hash, router查询)
    - 聊天记录查询
    - 异/同设备，多地登录
- 集群部署, docker swarm, k8s, jenkens
- 服务监控
- websocket 网关
- 考虑 dubbo 使用 grpc service


模块列表
- 接入层 entry
- 逻辑层 logic
- 内存存储层 router
- 固化存储层 das

功能组件：
1.0

- IOC: guice
- 通信层: netty
- 序列化: protobuf
- 内存存储: 
    - Caffeine
    - Memcache
- 分布式缓存：redis
- 注册中心：etcd、直连
- 处理队列：disruptor
- 持久化：
    - mysql 存储全量历史消息
    - mongodb 存储直接拉取的未读消息
- 日志：log4j2
- 监控：prometheus、grafana

- ID生成器

2.0

- quarkus

集群负载均衡：
子网1，子网2，公网
子网1和2不相通

dubbo native image
https://dubbo.apache.org/zh/docs/references/graalvm/support-graalvm/


entry <--> client 通信
- serviceId --> paramClass --> serviceHandler

entry <--> logic dubbo service
client jconsle cmd

router <--> cache
das <--> db
router <--> das


das shardingjdbc
table struct, sharding roles
logic biz

请求响应消息队列异步处理，减少线程 cpu 占用, dubbo async