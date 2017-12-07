
## Introduction



依赖注入

* 对比
	* 全面控制：内部bean由 外部bean代码内部创建，需要具体实现。   
	* 依赖注入：抽离出内部bean的创建，具体实现更灵活

* 实现方式：
	* 基于 set 方法。实现特定属性的public set方法，来让外部容器调用传入所依赖类型的对象。见com.tutorialspoint.chapter11.injection.setter
	* 基于构造函数。实现特定参数的构造函数，在新建对象时传入所依赖类型的对象。见com.tutorialspoint.chapter11.injection.constructor
	* 基于注解。基于Java的注解功能，在私有变量前加“@Autowired”等注解，不需要显式的定义以上三种代码，便可以让外部容器传入对应的对象。该方案相当于定义了public的set方法，但是因为没有真正的set方法，从而不会为了实现依赖注入导致暴露了不该暴露的接口（因为set方法只想让容器访问来注入而并不希望其他依赖此类的对象访问）。见com.tutorialspoint.chapter15.annotation.autowired.

* 代码示例：
	* 基于set方法：chapter 4 7 8 9 10 11 12 13
	* 基于构造函数： chapter 11
	* 基于注解：


|  chapter     |  简介 | 配置文件xml  | java代码   | inner bean | inner bean注入方式 | 备注|
| :-------- | -----:| ----:|--------:|--------:|:--: |
|  4  	  | 创建bean        |  &lt;property> | set  | 无 | - | 
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


* 实现原理:
	* 基于set方法：利用反射创建inner bean，然后调用set方法
	* 基于构造函数：利用反射创建inner bean，然后调用构造函数
	* 基于注解：暴力反射，利用AccessibleObject.setAccessible(true)，强行对私有变量赋值 (Field Method Constructor是AccessibleObject的子类)


## 源码


###通过XML配置创建bean
* 实例代码，见chapter 4
* 源码1

	ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");


* 源码2

	HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
*




###@Autowired
示例代码：见




主要流程：

1. get bean class
1. 实例化Bean: createBeanInstance(beanName, mbd, args)
1. 合并/注入bean，处理@Autowired：applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
	1. 
	1. 
1. 初始化Bean: initializeBean(beanName, exposedObject, mbd)

源码分析：

	package org.springframework.beans.factory.support;
	public abstract class AbstractAutowireCapableBeanFactory extends ...{
		...
		/**
		 * Central method of this class: creates a bean instance,
		 * populates the bean instance, applies post-processors, etc.
		 * mbd: xml中该bean的所有配置
		 * @see #doCreateBean
		 */
		Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args){
			// Instantiate the bean，利用反射调用构造函数
			BeanWrapper instanceWrapper = createBeanInstance(beanName, mbd, args);
			final Object bean = instanceWrapper.getWrappedInstance();
			
			// Allow post-processors to modify the merged bean definition.
			// 合并处理BeanDefinition，比如@Autowired注释的内部bean
			applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
			
			// Initialize the bean instance.
			populateBean(beanName, mbd, instanceWrapper);  // 这里会create
			Object exposedObject = initializeBean(beanName, bean, mbd);
			...
			
			return exposedObject;
		}
		
		void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName) {
			for (BeanPostProcessor bp : getBeanPostProcessors()) {
				if (bp instanceof MergedBeanDefinitionPostProcessor) {
					MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
					bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
				}
			}
		}
		
	}


	package org.springframework.beans.factory.annotation;
	public class AutowiredAnnotationBeanPostProcessor extends ...{
		public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
			InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType, null);
			metadata.checkConfigMembers(beanDefinition);
		}
		

		private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
			...
			InjectionMetadata  metadata = buildAutowiringMetadata(clazz); // !!!!!!!!
			return metadata;
		}

		
		
		private InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
			LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
			Class<?> targetClass = clazz;

			do {
				final LinkedList<InjectionMetadata.InjectedElement> currElements = new LinkedList<>();

				ReflectionUtils.doWithLocalFields(targetClass, field -> {
					AnnotationAttributes ann = findAutowiredAnnotation(field); //!!!!!!
					if (ann != null) {
						if (Modifier.isStatic(field.getModifiers())) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation is not supported on static fields: " + field);
							}
							return;
						}
						boolean required = determineRequiredStatus(ann);
						currElements.add(new AutowiredFieldElement(field, required));
					}
				});

				ReflectionUtils.doWithLocalMethods(targetClass, method -> {
					Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);
					if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
						return;
					}
					AnnotationAttributes ann = findAutowiredAnnotation(bridgedMethod);
					if (ann != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
						if (Modifier.isStatic(method.getModifiers())) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation is not supported on static methods: " + method);
							}
							return;
						}
						if (method.getParameterCount() == 0) {
							if (logger.isWarnEnabled()) {
								logger.warn("Autowired annotation should only be used on methods with parameters: " +
										method);
							}
						}
						boolean required = determineRequiredStatus(ann);
						PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, clazz);
						currElements.add(new AutowiredMethodElement(method, required, pd));
					}
				});

				elements.addAll(0, currElements);
				targetClass = targetClass.getSuperclass();
			}
			while (targetClass != null && targetClass != Object.class);

			return new InjectionMetadata(clazz, elements);
		}
		
		//
		private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
			if (ao.getAnnotations().length > 0) {
				for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
					AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);  //如果该field有@Autowired注释，那么返回{required=true}，否则返回null
					if (attributes != null) {
						return attributes;
					}
				}
			}
			return null;
		}
		
	}



##debug技巧
对于@Autowired注释Field，在AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement内部类的构造函数中设置断点。



###@Controller



## 疑问




## chapter4: bean creation

bean在new ClassPathXmlApplicationContext的时候，默认调用DefaultListableBeanFactory.preInstantiateSingletons()，创建bean。


## chapter7:

singleton
prototype
request
session
global-session

## chapter8: bean lifecycle




## chapter9: Spring——Bean 后置处理器


## chapter10: Bean 定义继承

仅仅是继承配置。不要求java类本身的extends，但要属性重叠

## chapter11: 依赖注入



## chapter15: 


