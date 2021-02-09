

## 简介

[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html)

## 源码

@SpringBootApplication源码如下：

```java
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.SpringBootConfiguration; // 这里有些猫腻吧
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};
}
```

从源代码中得知 @SpringBootApplication 是一个复合注解， 
它被 `@Configuration`、`@EnableAutoConfiguration`、`@ComponentScan` 注解所修饰。
换言之
`@SpringBootApplication`=`@SpringBootConfiguration`+`@EnableAutoConfiguration`+`@ComponentScan`，
简化程序的配置。

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```


> springboot看上去就是spring的一个封装。

## 分解

### SpringBootConfiguration

`@SpringBootConfiguration`继承自`@Configuration`，二者功能也一致，标注当前类是配置类，
并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到spring容器中，
并且实例名就是方法名。

### @EnableAutoConfiguration



### @ComponentScan 扫描范围

其中扫描包的范围为启动类所在包和子包，不包括第三方的jar包。如果我们需要扫描通过maven依赖添加的jar，我们就要单独使用@ComponentScan注解扫描第三方包。

扫描当前包及其子包下被@Component，@Controller，@Service，@Repository注解标记的类并纳入到spring容器中进行管理。

是以前的`<context:component-scan>`（以前使用在xml中使用的标签，用来扫描包配置的平行支持）。


如果@SpringBootApplication和@ComponentScan注解共存，那么@SpringBootApplication注解的扫描的作用将会失效，也就是说不能够扫描启动类所在包以及子包了。因此，我们必须在@ComponentScan注解配置本工程需要扫描的包范围。引用官网的说明：

### s

## aliases


## 我的理解 & 疑问

spring-boot就是包装了一下spring和tomcat，简化流程而已吧。后台逻辑并未轻量化吧？


