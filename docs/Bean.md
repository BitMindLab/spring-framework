




## POJO


POJO的内在含义是指那些:
有一些private的参数作为对象的属性，然后针对每一个参数定义get和set方法访问的接口。
没有从任何类继承、也没有实现任何接口，更没有被其它框架侵入的java对象。

## Bean

- 所有属性为private。
- 这个类必须有一个公共的缺省构造函数。即是提供无参数的构造器。
- 这个类的属性使用getter和setter来访问，其他方法遵从标准命名规范。
- 这个类应是可序列化的。实现serializable接口。


## 两者有什么区别

POJO其实是比javabean更纯净的简单类或接口。POJO严格地遵守简单对象的概念，而一些JavaBean中往往会封装一些简单逻辑。

POJO主要用于数据的临时传递，它只能装载数据， 作为数据存储的载体，而不具有业务逻辑处理的能力。

Javabean虽然数据的获取与POJO一样，但是javabean当中可以有其它的方法。

POJO没有业务方法吗？