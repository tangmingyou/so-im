
其他服务到 router 的服务，通过 uid 进行一致性 hash 负载均衡，

router 到 entry 的服务，通过指定 user 所登录的 entry 节点 serverAddr 地址进行指向性路由负载均衡。

