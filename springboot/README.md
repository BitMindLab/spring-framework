
Spring Boot很容易创建一个独立运行（运行jar,内嵌Servlet容器）


## 简介

## 微服务是什么？

微服务(Micro Service)是一种允许开发人员独立开发和部署服务的体系结构。每个运行的服务都有自己的流程，这实现了轻量级模型以支持业务应用程序。

## spring boot


Spring Boot是伴随着Spring4.0 产生的，是由Pivotal团队提供的全新框架，
其设计目的是用来**简化新Spring应用的初始搭建以及开发过程**。该框架使用了特定的方式进行配置，
从而使开发人员不再需要定义样板化的配置。通过这种方式，Boot致力于在蓬勃发展的快速应用开发领域（rapid application development）成为领导者。


Spring Boot让我们的Spring应用变得更轻量化。比如：你可以仅仅依靠一个Java类来运行Spring引用。
你也可以打包你的应用为jar并通过使用`java –jar`来运行你的Spring Web应用。


### Spring Boot的主要优点：

- 为所有Spring开发者更快的入门
- 开箱即用，提供各种默认配置来简化项目配置
- 内嵌式容器简化web项目
- 没有冗余代码生成和xml配置的要求
- 尽可能的根据项目依赖来自动配置Spring框架。
- 提供可以直接在生产环境中使用的功能，如性能指标，应用信息和应用健康检查。


### Spring Boot的缺点
- 依赖太多，随便的一个Spring Boot应用都有好几十M
- 缺少服务的注册和发现等解决方案，可以结合springcloud的组件使用。
- 缺少监控集成方案、安全管理方案（有但简单，满足不了生产的指标）


### Spring Boot的设计目标如下 -

- 避免在Spring中进行复杂的XML配置
- 以更简单的方式开发生产就绪的Spring应用程序
- 减少开发时间并独立运行应用程序
- 提供一种更简单的应用程序入门方式原文出自

### Spring Boot是如何工作的？



## 内嵌的servlet

内嵌的servlet有tomcat，有jetty。默认是tomcat

tomcat: 
    - `org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer`
    - `org.springframework.boot.web.embedded.tomcat.TomcatWebServer`

jetty: 
    - `org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer`
    - `org.springframework.boot.web.embedded.jetty.JettyWebServer` 在8080端口启动http服务
Undertow

在`spring-boot.jar`中，默认容器是Tomcat

**替换为Jetty**: 将spring-boot-starter-web的依赖由spring-boot-starter-tomcat替换为spring-boot-starter-Jetty:
**替换为Undertow**: 在pom.xml中，将spring-boot-starter-web的依赖由spring-boot-starter-tomcat替换为spring-boot-starter-undertow:

# 笔记

controller跟


## Spring Boot引导过程

https://www.yiibai.com/spring-boot/spring_boot_bootstrapping.html

## Spring Boot Tomcat部署


## Spring Boot Bean和依赖注入


在Spring Boot中，可以使用Spring Framework来定义bean及其依赖注入。 @ComponentScan注释用于查找bean以及使用@Autowired注释注入的相应内容。
如果遵循Spring Boot典型布局，则无需为@ComponentScan注释指定任何参数。 所有组件类文件都自动注册到Spring Beans。原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/spring-boot/spring_boot_beans_and_dependency_injection.html




# 相关链接

- https://www.yiibai.com/spring-boot/spring_boot_introduction.html
- [Spring Boot学习系列](https://github.com/tengj/SpringBootDemo)

