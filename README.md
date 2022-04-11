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