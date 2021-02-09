---
title: spring boot的部署
---




## 使用tomcat7-maven-plugin部署Web项目

```xml


```



## spring-boot-maven-plugin

```xml
<build> 
  <plugins> 
    <plugin> 
      <groupId>org.springframework.boot</groupId>  
      <artifactId>spring-boot-maven-plugin</artifactId>  
      <configuration> 
        <executable>true</executable> 
      </configuration> 
    </plugin> 
  </plugins> 
</build>
```
注意设置`executable`配置。这样我们可以不用java -jar，而是直接运行jar来执行程序。


使用mvn install进行打包，构建一个可执行的jar包


## 参考

