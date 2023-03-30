## 项目介绍

自然语言分词展示管理系统是为分词处理结果数据做了一个可视化的展示，对分词结果数据针对需求进行了处理加工，可以满足对分词结果数据的统计分析，包括对分词结果数据进行频率的计算，按照模型的词性标注集的词性进行筛选，并结合柱状图和饼状图进行可视化的展示，以此有助于自然语言处理分词模型算法的研究，帮助初学者更好的分析模型算法的可行性和不足。


## 系统功能模块


### 数据集管理功能模块

该功能可以对常用数据集进行维护，条件查询数据集信息，新增、编辑、删除和批量删除数据集信息，查看数据集详情和数据集内容详情，还具有将数据集数据导出为Excel表格文件，同时可以将数据集数据Excel表格文件进行导入等功能。 
### 模型管理功能模块

该功能可以对常用模型进行维护，条件查询模型信息，新增、编辑、启用、禁用、删除和批量删除模型信息，查看模型详情，还具有将模型数据导出为Excel表格文件，同时可以将模型数据Excel表格文件进行导入等功能。 
### 词性管理功能模块

该功能可以对常用词性标注集信息进行维护，条件查询词性标注集信息，新增、编辑、删除和批量删除词性标注集信息和词性标注集明细信息，查看词性标注集详情和词性标注集明细详情，还具有将词性标注集数据导出为Excel表格文件，同时可以将词性标注集数据Excel表格文件进行导入等功能。
### 模型演示功能模块

该功能实现对客户自输入的文本内容进行分词结果的展示，客户在输入框输入文本内容后选择调用模型输出分词结果，可以进行词性筛选、展示词性、查看分词结果、查看词性标注集等功能。 
### 分词展示功能模块

该功能实现对客户选择数据集进行分词结果的展示，客户在选择数据集后选择调用模型输出分词结果，可以进行词性筛选、展示词性、查看数据集内容、查看分词结果、查看词性标注集等功能。
### 分词对比模块

该功能实现对客户选择多个模型对同一数据集进行分词结果的对比展示，目前存在bug，待开发完善。

 
## 技术架构：

#### 开发环境

- 语言：Java 8

- IDE(JAVA)： IDEA / Eclipse安装lombok插件 

- IDE(前端)： WebStorm 或者 IDEA

- 依赖管理：Maven

- 缓存：Redis

- 数据库脚本：MySQL5.7+  &  Oracle 11g & Sqlserver2017（默认只提供这三个库脚本，其他库需要自己转）


#### 后端

- 基础框架：Spring Boot 2.3.5.RELEASE、Jdk8+ (小于11)

- 微服务框架： Spring Cloud Alibaba 2.2.3.RELEASE

- 持久层框架：MybatisPlus 3.4.3.1 

- 报表工具： JimuReport 1.4.32

- 安全框架：Apache Shiro 1.8.0，Jwt 3.11.0

- 微服务技术栈：Spring Cloud Alibaba、Nacos、Gateway、Sentinel、Skywalking

- 数据库连接池：阿里巴巴Druid 1.1.22

- 缓存框架：redis

- 日志打印：logback

- 其他：autopoi, fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。


#### 前端
 
- [Vue 2.6.10](https://cn.vuejs.org/),[Vuex](https://vuex.vuejs.org/zh/),[Vue Router](https://router.vuejs.org/zh/)
- [Axios](https://github.com/axios/axios)
- [ant-design-vue](https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn/)
- [webpack](https://www.webpackjs.com/),[yarn](https://yarnpkg.com/zh-Hans/)
- [vue-cropper](https://github.com/xyxiao001/vue-cropper) - 头像裁剪组件
- [@antv/g2](https://antv.alipay.com/zh-cn/index.html) - Alipay AntV 数据可视化图表
- [Viser-vue](https://viserjs.github.io/docs.html#/viser/guide/installation)  - antv/g2 封装实现
- eslint，[@vue/cli 3.2.1](https://cli.vuejs.org/zh/guide)
- vue-print-nb - 打印


#### 支持库

|  数据库   |  支持   |
| --- | --- |
|   MySQL   |  √   |
|  Oracle11g   |  √   |
|  Sqlserver2017   |  √   |
|   PostgreSQL   |  √   |
|   DB2、Informix   |  √   |
|   MariaDB   |  √   |
|  SQLite、Hsqldb、Derby、H2   |  √   |
|   达梦、人大金仓、神通   |  √   |
|   华为高斯、虚谷、瀚高数据库   |  √   |
|   阿里云PolarDB、PPAS、HerdDB   |  √   |
|  Hive、HBase、CouchBase   |  √   |


## 微服务解决方案


1、服务注册和发现 Nacos √

2、统一配置中心 Nacos  √

3、路由网关 gateway(三种加载方式) √

4、分布式 http feign √

5、熔断和降级 Sentinel √

6、分布式文件 Minio、阿里OSS √ 

7、统一权限控制 JWT + Shiro √

8、服务监控 SpringBootAdmin√

9、链路跟踪 Skywalking   [参考文档](https://www.kancloud.cn/zhangdaiscott/jeecgcloud/1771670)

10、消息中间件 RabbitMQ  √

11、分布式任务 xxl-job  √ 

12、分布式事务 Seata

13、分布式日志 elk + kafka

14、支持 docker-compose、k8s、jenkins

15、CAS 单点登录   √

16、路由限流   √

   
#### 微服务架构图
![微服务架构图](https://jeecgos.oss-cn-beijing.aliyuncs.com/files/jeecgboot-weifuwu-cloud.png "在这里输入图片标题")

### Jeecg Boot 产品功能蓝图
![功能蓝图](https://jeecgos.oss-cn-beijing.aliyuncs.com/upload/test/Jeecg-Boot-lantu202005_1590912449914.jpg "在这里输入图片标题")
