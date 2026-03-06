# Nacos控制台配置指南

## 1. Nacos控制台访问

### 1.1 访问地址

打开浏览器，访问以下地址：

```
http://192.168.118.130:8848/nacos
```

### 1.2 登录信息

- **用户名**：nacos
- **密码**：nacos

## 2. 命名空间管理

### 2.1 创建命名空间

1. 登录Nacos控制台后，点击左侧菜单中的「命名空间」
2. 点击「新建命名空间」按钮
3. 填写命名空间信息：
   - **命名空间ID**：dev
   - **命名空间名称**：开发环境
   - **描述**：开发环境配置
4. 点击「确定」按钮

### 2.2 选择命名空间

在控制台顶部的命名空间下拉菜单中选择「dev」命名空间，确保所有配置都在正确的命名空间中创建。

## 3. 配置管理

### 3.1 创建配置文件

#### 3.1.1 数据源配置

1. 点击左侧菜单中的「配置管理」→「配置列表」
2. 点击「+」按钮新建配置
3. 填写配置信息：
   - **Data ID**：datasource-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://192.168.118.130:3306/energy_monitor?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
         username: root
         password: root123
         driver-class-name: com.mysql.cj.jdbc.Driver

       data:
         redis:
           host: 192.168.118.130
           port: 6379
     ```
4. 点击「发布」按钮

#### 3.1.2 认证服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：auth-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8081

     spring:
       application:
         name: auth-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml

     # 认证相关配置
     auth:
       jwt:
         secret: energy_monitor_secret_key
         expiration: 86400
     ```
3. 点击「发布」按钮

#### 3.1.3 设备服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：device-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8082

     spring:
       application:
         name: device-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
     ```
3. 点击「发布」按钮

#### 3.1.4 数据采集服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：data-collection-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8083

     spring:
       application:
         name: data-collection-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
     ```
3. 点击「发布」按钮

#### 3.1.5 数据处理服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：data-process-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8084

     spring:
       application:
         name: data-process-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
     ```
3. 点击「发布」按钮

#### 3.1.6 数据存储服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：data-storage-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8085

     spring:
       application:
         name: data-storage-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
     ```
3. 点击「发布」按钮

#### 3.1.7 告警服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：alert-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8086

     spring:
       application:
         name: alert-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
     ```
3. 点击「发布」按钮

#### 3.1.8 网关服务配置

1. 点击「+」按钮新建配置
2. 填写配置信息：
   - **Data ID**：gateway-service-dev.yml
   - **Group**：ENERGY_PLATFORM
   - **配置格式**：YAML
   - **配置内容**：
     ```yaml
     server:
       port: 8080

     spring:
       application:
         name: gateway-service
       cloud:
         nacos:
           discovery:
             server-addr: 192.168.118.130:8848
             namespace: dev
           config:
             server-addr: 192.168.118.130:8848
             namespace: dev
             group: ENERGY_PLATFORM
             file-extension: yml
         gateway:
           routes:
             - id: auth-service
               uri: lb://auth-service
               predicates:
                 - Path=/api/auth/**
             - id: device-service
               uri: lb://device-service
               predicates:
                 - Path=/api/devices/**
             - id: data-collection-service
               uri: lb://data-collection-service
               predicates:
                 - Path=/api/data/collect/**
             - id: data-process-service
               uri: lb://data-process-service
               predicates:
                 - Path=/api/data/process/**
             - id: data-storage-service
               uri: lb://data-storage-service
               predicates:
                 - Path=/api/data/storage/**
             - id: alert-service
               uri: lb://alert-service
               predicates:
                 - Path=/api/alerts/**
     ```
3. 点击「发布」按钮

## 4. 服务管理

### 4.1 查看服务列表

1. 点击左侧菜单中的「服务管理」→「服务列表」
2. 确认所有服务都已注册
3. 查看服务的健康状态

### 4.2 服务注册验证

启动所有后端服务后，服务列表中应该显示以下服务：

- auth-service
- device-service
- data-collection-service
- data-process-service
- data-storage-service
- alert-service
- gateway-service

## 5. 配置管理最佳实践

### 5.1 配置命名规范

- **Data ID**：服务名-环境.yml（例如：auth-service-dev.yml）
- **Group**：项目标识（例如：ENERGY_PLATFORM）
- **命名空间**：环境标识（例如：dev、test、prod）

### 5.2 配置分层

- **通用配置**：数据源、Redis等通用配置单独创建
- **服务配置**：每个服务的特定配置单独创建
- **环境配置**：不同环境的配置使用不同的命名空间

### 5.3 配置更新

1. 在Nacos控制台中修改配置
2. 点击「发布」按钮
3. 服务会自动感知配置变化并更新

### 5.4 配置回滚

1. 在配置详情页面，点击「历史版本」
2. 选择需要回滚的版本
3. 点击「回滚到此版本」

## 6. 常见问题排查

### 6.1 服务注册失败

- 检查Nacos地址是否正确
- 检查服务名称是否正确
- 检查网络连接是否正常
- 查看服务启动日志

### 6.2 配置不生效

- 检查Data ID是否正确
- 检查Group是否正确
- 检查命名空间是否正确
- 检查配置格式是否正确

### 6.3 服务健康状态异常

- 检查服务是否正常运行
- 检查服务的健康检查接口是否正常
- 检查服务依赖的资源是否可用

## 7. 总结

通过以上步骤，您可以在Nacos控制台中完成以下操作：

1. 创建和管理命名空间
2. 创建和发布配置文件
3. 查看和管理服务注册
4. 监控服务健康状态
5. 管理配置版本和回滚

Nacos作为服务注册与配置中心，为微服务架构提供了强大的服务治理能力，确保系统的稳定性和可靠性。
