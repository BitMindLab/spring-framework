

> This annotation serves as a specialization of @Component, allowing for implementation classes to be autodetected through classpath scanning. It is typically used in combination with annotated handler methods based on the RequestMapping annotation.
-- 来自 [官方文档](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Controller.html)


## VS @Resource

@Autowired 可以根据类型注入对象，@Qualifier 可以根据 bean 名称注入对象。那 @Resource 的作用是什么呢？@Resource 属于 JSR-250 规范定义的注解，支持根据类型和名称进行对象注入。@Resource 注解始于 jdk1.6。

该注解不属于 Spring