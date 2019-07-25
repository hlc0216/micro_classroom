# 微课堂

#### 项目介绍
微课堂是一个简单高效的后台权限管理系统。项目基础框架采用全新的Java Web开发框架 —— Spring Boot2.0.4，消除了繁杂的XML配置，使得二次开发更为简单；数据访问层采用Mybatis，同时引入了通用Mapper和PageHelper插件，可快速高效的对单表进行增删改查操作，消除了大量传统XML配置SQL的代码；安全框架采用Spring Security 5.0.7，可实现对按钮级别的权限控制，并集成了社交账户登录（QQ和微信）以及手机验证码登录；前端页面使用Bootstrap构建，主题风格为时下Google最新设计语言Material Design，并提供多套配色以供选择

## 软件架构
> 技术选型
### 后端
 * 基础框架：Spring Boot 2.0.4.RELEASE
 * 持久层框架：Mybatis 3.4.5
 * 安全框架：Spring Security 5.0.7
 * 摸板引擎：Thymeleaf 3.0.9.RELEASE
 * 数据库连接池：Hikari
 * 缓存框架：Redis
 * 日志打印：logback
 * 其他：Spring Social，Spring OAuth2，fastjson，poi，javacsv，quartz等。
### 前端
 * 基础框架：Bootstrap 4
 * JavaScript框架：jQuery
 * 消息组件：Bootstrap notify
 * 提示框插件：SweetAlert2
 * 树形插件：jsTree
 * 树形表格插件：jqTreeGrid
 * 表格插件：BootstrapTable
 * 表单校验插件：jQuery-validate
 * 多选下拉框插件：multiple-select
 * 图表插件：Highcharts
 * 时间插件：daterangepicker
### 开发环境
 * 语言：Java 8
 * IDE：Eclipse Oxygen & IDEA 2018.1.4(Ultimate Edition)
 * 依赖管理：Maven
 * 数据库：MySQL5.
### 版本管理
git

## 环境准备
### JDK

因为项目用到了JDK 8的一些特性，所以JDK最低版本不能低于8。

JDK 8官方下载地址：[https://www.oracle.com/technetwork/java/javase/downloads](http://www.oracle.com/technetwork/java/javase/downloads)。

### Redis
项目缓存数据库使用的是Redis，所以在导入项目前需先安装Redis。
Redis Windows版本下载地址：[https://github.com/MicrosoftArchive/redis/releases](https://github.com/MicrosoftArchive/redis/releases)
。直接下载zip版本解压到任意目录即可。
下载后，使用cmd命令切换到Redis根目录，然后运行`redis-server.exe redis.windows.conf`动即可。项目中与Redis相关的配置可在application.yml中修改：

```
spring:
  redis:
    # Redis数据库索引（默认为0）
    database: 0
    # Redis服务器地址
    host: 127.0.0.1
    # Redis服务器连接端口
    port: 6379
    # Redis 密码
    password:
    jedis:
      pool:
        # 连接池中的最小空闲连接
        min-idle: 8
        # 连接池中的最大空闲连接
        max-idle: 500
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 2000
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 10000
    # 连接超时时间（毫秒）
    timeout: 0
```
### MySQL
项目数据库采用MySQL社区版，版本为5.7。
下载地址：[：https://dev.mysql.com/downloads/windows/installer/5.7.html](：https://dev.mysql.com/downloads/windows/installer/5.7.html)
别的版本的MySQL也可以，但需要根据实际情况修改对应的SQL语句。


## 安装教程

### git clone
安装了git的用户也可以直接使用下面的命令进行克隆：

```
git clone https://github.com/hlc0216/micro_classroom
```
### 项目导入
IDE工具选择Eclipse或者Intellij IDEA均可。

### 创建数据库

导入完项目后，接下来开始创建数据库。使用Navicat（或者别的数据库客户端）创建一个新的数据库，名称为febs_security（或者别的你喜欢的名字），并选择UTF8编码：

![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/192514_c1c46169_1876174.png "QQ截图20180910161305.png")

![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/192611_51cf872e_1876174.png "QQ截图20180910162759.png")
> 如果数据库版本不是5.7的在执行SQL文件的时候会报错，这时候只需要根据报错信息简单修改SQL语句，然后重新执行即可。
创建完数据库，接着导入SQL文件。在项目的根目录下有个名称为febs_security.sql的SQL文件：
这里简单描述一下这些表的作用。

其中T_开头的表为系统表：

| 表名              | 描述                                                 |
| ----------------- | ---------------------------------------------------- |
| persistent_logins | 用于存储“记住我”的token信息                        |
| t_userconnection  | 用于存储社交账号绑定关系                             |
| t_user            | 用户表，用于存储用户信息                             |
| t_role            | 角色表，用于存储角色信息                             |
| t_user_role       | 用于关联用户和角色，目的在于为用户分配角色           |
| t_menu            | 菜单表，用于存储菜单和按钮信息（以及相应的权限）     |
| t_role_menu       | 用于关联角色和菜单，目的在于为角色分配菜单和按钮权限 |
| t_dept            | 部门表，用于存储部门信息                             |
| t_dict            | 字典表，用于存储字典信息                             |
| t_log | 日志表，用于存储用户操作日志 |
| t_job | 	调度任务表，用于存储调度任务信息
| t_job_log |	调度日志表，用于存储任务调度日志 |

这里对于用户，角色和权限之间的关系使用的是经典的RBAC（Role-Based Access Control，基于角色的访问控制）模型。简单地说，一个用户拥有若干角色，每一个角色拥有若干权限。这样，就构造成“用户-角色-权限”的授权模型。在这种模型中，用户与角色之间，角色与权限之间，一般者是多对多的关系。如下图所示：
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/193127_0794938a_1876174.png "QQ截图20171214123601.png")
在本项目中，权限相关的信息位于T_MENU表的perms字段中，并没有单独将权限拆分出来。这样设计是为了让权限和菜单/按钮的对应关系更为直观，也减少了不必要的数据表关联。

剩下的qrtz_开头的表用于维护任务调度。

项目中数据库链接信息可在application.yml中修改：

```
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/febs_security?useUnicode=true&characterEncoding=utf8
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      connection-test-query: select 1
      max-lifetime: 1800000
      connection-timeout: 30000
      pool-name: FebsHikariCP
```

### 启动项目

在完成了上述步骤后，找到FebsApplication启动类（位于febs-web模块），右键选择 Run ‘Application’即可。

启动后访问地址为：[http://localhost](http://localhost)，用户名：alex，密码：123456。




## 模块说明
> 系统分为以下五个模块：

| 模块          | 说明                                   |
| ------------- | -------------------------------------- |
| febs-common   | 基础模块，主要包含一些工具类，基础配置 |
| febs-system   | 系统模块，增删改查服务                 |
| febs-quartz   | 任务调度模块，处理定时任务             |
| febs-security | 安全模块，和安全有关的都在这个模块里   |
| febs-web      | web模块，包含前端部分和控制层          |

## 效果图
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194046_d1963f1d_1876174.png "QQ浏览器截图20190113193645.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194058_807fdac7_1876174.png "QQ浏览器截图20190113193701.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194109_ffb448ba_1876174.png "QQ浏览器截图20190113193716.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194127_540081a3_1876174.png "QQ浏览器截图20190113193724.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194139_d4ea1ba2_1876174.png "QQ浏览器截图20190113193736.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194150_13c08621_1876174.png "QQ浏览器截图20190113193756.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194202_8761cc29_1876174.png "QQ浏览器截图20190113193803.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194212_ae879f9f_1876174.png "QQ浏览器截图20190113193821.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194223_55a262f2_1876174.png "QQ浏览器截图20190113193827.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194235_f3bff05b_1876174.png "QQ浏览器截图20190113193834.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194246_2c3e879a_1876174.png "QQ浏览器截图20190113193857.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0113/194256_771ea8cc_1876174.png "QQ浏览器截图20190113193920.png")
