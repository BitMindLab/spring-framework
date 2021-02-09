

## 简介

from https://spring.io/guides/gs/securing-web/


## maven配置

### spring-boot-starter-parent



Maven的用户可以通过继承spring-boot-starter-parent项目来获得一些合理的默认配置。这个parent提供了以下特性：

- 默认使用Java 8
- 使用UTF-8编码
- 一个引用管理的功能，在dependencies里的部分配置可以不用填写version信息，这些version信息会从spring-boot-dependencies里得到继承。
- 识别过来资源过滤（Sensible resource filtering.）
- 识别插件的配置（Sensible plugin configuration (exec plugin, surefire, Git commit ID, shade).）
- 能够识别application.properties和application.yml类型的文件，同时也能支持profile-specific类型的文件（如： application-foo.properties and application-foo.yml，这个功能可以更好的配置不同生产环境下的配置文件)。
- maven把默认的占位符${…​}改为了@..@（这点大家还是看下原文自己理解下吧，我个人用的也比较少 
since the default config files accept Spring style placeholders (${…​}) the Maven filtering is changed to use @..@ placeholders (you can override that with a Maven property resource.delimiter).）

### spring-boot-maven-plugin

用于把项目打包成一个可执行的jar包

- It collects all the jars on the classpath and builds a single, 
runnable "über-jar", which makes it more convenient to execute and transport your service.
- It searches for the `public static void main()` method to flag as a runnable class.
    - 多个main，会怎样？自动搜索main，是不是启动就不用指定class了？
- It provides a built-in dependency resolver that sets the version number to match Spring Boot dependencies. You can override any version you wish, but it will default to Boot’s chosen set of versions.


## template

### `@{/hello}`, `@{/login}`, `@{/logout}`, 在哪定义的？

`MvcConfig.java`中加入了ViewController，

### `${param.error}` `${param.logout}`在哪定义的？


### 前后端彻底分离呢？前端不依赖spring该怎样设计？


### ss

## 疑问

为什么debug，断点不停？
