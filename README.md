# ASFBBS

#### 介绍
ASF2.0后端

#### 软件架构
springcloud gateway网关入口 -> 应用服务集群 -> nacos中心集群
docker 部署本地nacos 
docker pull docker.io/nacos/nacos-server
run --env MODE=standalone --name nacos -d -p 8848:8848 nacos/nacos-server

#### 本地环境

1.  MySQL
2.  Redis
3.  Nacos

#### 使用说明

1.  依次启动 AsfbbsGateway -> AsfbbsAuth -> 其他服务

