Jeecg-Boot 低代码开发平台
===============

当前最新版本： 3.1.0（发布日期：20220301）

## 后端技术架构

- 基础框架：Spring Boot 2.3.5.RELEASE

- 微服务框架： Spring Cloud Alibaba 2.2.3.RELEASE

- 微服务技术栈：Spring Cloud Alibaba、Nacos、Gateway、Sentinel、Skywalking

- 持久层框架：Mybatis-plus 3.4.3.1

- 安全框架：Apache Shiro 1.7.0，Jwt 3.11.0

- 数据库连接池：阿里巴巴Druid 1.1.22

- 报表工具： JimuReport 1.4.32

- 缓存框架：redis3.2+

- 日志打印：logback

- 其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。

## 开发环境

- 语言：Java 8+ (小于11)

- IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

- 依赖管理：Maven3.5+

- 数据库：MySQL5.7+  &  Oracle 11g & SqlServer & postgresql & 国产等更多数据库

- 缓存：Redis3.2+

## 项目启动

**微服务架构服务启动顺序：** **参考官方文档** https://doc.jeecg.com/3043471 

**准备工作：** 启动本地redis服务、执行数据库初始化脚本（jeecg-boot/db/jeecgboot-mysql-5.7.sql、jeecg-boot/db/tables_nacos.sql、jeecg-boot/db/tables_xxl_job.sql、jeecg-boot/db/NLP相关sql脚本）。

**1. 启动`nacos`：** 

**找到下面的启动类，右键执行：**

```
项目：jeecg-server-cloud\jeecg-cloud-nacos
启动类：com.alibaba.nacos.JeecgNacosApplication
```

**访问：** http://localhost:8848/nacos，账号密码 nacos/nacos，修改在线配置文件（jeecg-dev.yaml），如mysql数据库连接等信息。

**2. 启动`jeecg-demo`服务：**

**找到下面的启动类打开注释，右键启动：**

```
项目：jeecg-server-cloud/jeecg-demo-cloud-start
启动类：org.jeecg.JeecgDemoCloudApplication
```

**3. 启动`jeecg-system`服务：**

**找到下面的启动类，右键启动：**

```
项目：jeecg-server-cloud/jeecg-system-cloud-start
启动类：org.jeecg.JeecgSystemCloudApplication
```

**4. 启动网关`gateway`：**

**找到下面的启动类，右键启动：**

```
项目：jeecg-server-cloud\jeecg-cloud-gateway
启动类：org.jeecg.JeecgGatewayApplication
```

**访问gateway看接口文档：** [http://localhost:9999](http://localhost:9999/)

## 技术文档


- 在线演示 ：  [http://boot.jeecg.com](http://boot.jeecg.com)
- 在线文档：  [http://doc.jeecg.com](http://doc.jeecg.com)
- 常见问题：  [http://jeecg.com/doc/qa](http://jeecg.com/doc/qa)

## 代码生成器

**功能说明：**   一键生成的代码（包括：controller、service、dao、mapper、entity、vue）

 - **模板位置：** src/main/resources/jeecg/code-template
 - **技术文档：** http://doc.jeecg.com/2043916
