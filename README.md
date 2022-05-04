TODO 
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