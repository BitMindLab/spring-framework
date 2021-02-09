
## 简介

* [英文文档](https://www.tutorialspoint.com/spring/)
* [中文翻译](http://wiki.jikexueyuan.com/project/spring/)
* [中文pdf文档]()





## 目录

| chapter |  简介           | 配置文件xml    | java代码   | inner bean | inner bean注入方式 | 备注|
| ------------- |:-------------:| -----:| :-------------:| -----:| -------------:| -----:|
| 4  	  | 创建bean        |  &lt;property> | set  | 无 | - | 
| 7       | Bean的作用域     | scope="singleton" or "prototype"| set |无 | - |  |
| 8       | Bean的生命周期   | init-method=".." destroy-method=".."|set init destroy  | 无 | - |   |
| 9       | Bean的后置处理器 |   | BeanPostProcessor实现类 | 无 | - |  |
| 10      | Bean定义继承     | parent=  | | 无 | - |   |
| 11      | 依赖注入 | &lt;constructor-arg>或&lt;property>|  | 无 | - | |
| 12      | 注入内部bean     | &lt;property>嵌套&lt;bean> | set | 有  | setter |  |
| 13      | 注入集合     |  | |  | | | |
| 14      | Beans自动装配     | autowire=".." 代替&lt;property ref=> | set | 有  |  setter | **缩减了xml** |
| 15      | 基于注解的配置 | ~~&lt;property>~~ | @Autowired ~~set~~| 有  | annotation| **进一步缩减了xml & java的set方法** |
| 16      | 基于 Java 的配置 |无 |  @Configuration @Bean | 无  | annotation| **利用java config类取代xml**|
| 17      | 事件处理 | |  ApplicationListener实现类 | 无  | setter| **利用java config类取代xml**|
| 19      | aop     | |   |   | | |




### 通过XML配置创建bean
* 实例代码，见chapter 4
* 源码1

	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");


* 源码2

	HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
*







