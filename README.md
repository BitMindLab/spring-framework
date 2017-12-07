
## Introduction


## tutorial

* [tutorialspoint ](/tutorial)
* [spring in action](/springinaction)


## 依赖注入

* 对比
	* 全面控制：内部bean由 外部bean代码内部创建，需要具体实现。   
	* 依赖注入：抽离出内部bean的创建，具体实现更灵活

* 实现方式：
	* 基于 set 方法。实现特定属性的public set方法，来让外部容器调用传入所依赖类型的对象。见com.tutorialspoint.chapter11.injection.setter
	* 基于构造函数。实现特定参数的构造函数，在新建对象时传入所依赖类型的对象。见com.tutorialspoint.chapter11.injection.constructor
	* 基于注解。基于Java的注解功能，在私有变量前加“@Autowired”等注解，不需要显式的定义以上三种代码，便可以让外部容器传入对应的对象。该方案相当于定义了public的set方法，但是因为没有真正的set方法，从而不会为了实现依赖注入导致暴露了不该暴露的接口（因为set方法只想让容器访问来注入而并不希望其他依赖此类的对象访问）。见com.tutorialspoint.chapter15.annotation.autowired.

* 实现原理:
	* 基于set方法：利用反射创建inner bean，然后调用set方法
	* 基于构造函数：利用反射创建inner bean，然后调用构造函数
	* 基于注解：暴力反射，利用AccessibleObject.setAccessible(true)，强行对私有变量赋值 (Field Method Constructor是AccessibleObject的子类)



* 代码示例：
	* 基于set方法：chapter 4 7 8 9 10 11 12 13
	* 基于构造函数： chapter 11
	* 基于注解：
	
## 源码分析:
[@Autowired](docs/@Autowired.md)
[@Controller](docs/@Controller.md)