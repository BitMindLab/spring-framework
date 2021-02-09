---
title: @ComponentScan 自动扫描组件
---


## @ComponentScan注解是什么



其实很简单，@ComponentScan主要就是定义扫描的路径从中**找出标识了需要装配的类自动装配到spring的bean容器中**


1.创建一个配置类，在配置类上添加 @ComponentScan 注解。该注解默认会扫描该类所在的包下所有的配置类，相当于之前的 `<context:component-scan>`。



## @ComponentScan注解的详细使用


做过web开发的同学一定都有用过@Controller，@Service，@Repository注解，
查看其源码你会发现，他们中有一个共同的注解@Component，没错@ComponentScan注解默认就会装配标识了@Controller，@Service，@Repository，@Component注解的类到spring容器中，
好下面咱们就先来简单演示一下这个例子


## 多种扫描规则

### 排除规则

```java
@Configuration
@ComponentScan(basePackages = "me.sjl.*", excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = Controller.class)
})
public class ScanConfig {
}
```
excludeFilters 属性表示排除规则， 可以写多个 @Filter 来排除， 上面的代码表示 排除 Controller 注解的类

```
ANNOTATION, 基于注解的过滤
ASSIGNABLE_TYPE, 指定class
ASPECTJ,ASPECTJ 表达shi
REGEX, 正则表达式
CUSTOM; 自定义
```
