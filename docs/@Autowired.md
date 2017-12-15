# Autowired源码分析

## 示例代码

见[com.tutorialspoint.chapter14.autowiring.byname](../tutorialspoint/com/tutorialspoint/chapter14/autowiring/byname)


## 主要流程：

1. 实例化RootBean，调用无参构造函数:
1. 合并BeanDefinition  (不创建bean)：查找RootBean中@Autowired注解信息，与xml中的BeanDefinition合并存入mbd
1. 初始化RootBean: 
	1. 根据mbd创建innerBean
	1. 根据mbd初始化RootBean，包括**set方法**(针对具有public set方法的field)，**依赖注入**(针对@Autowired注解的Field，采用暴力反射setField)

## 源码分析：

**整体架构**

```java
package org.springframework.beans.factory.support;
public abstract class AbstractAutowireCapableBeanFactory extends ...{
	...
	/**
	 * Central method of this class: creates a bean instance,
	 * populates the bean instance, applies post-processors, etc.
	 * mbd: RootBeanDefinition，包含xml的bean信息以及注解中的bean信息。
	 * @see #doCreateBean
	 */
	Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args){
		// 1. 利用反射创建RootBean，(调用无参构造函数)
		//    内部核心函数ctor.newInstance(args)
		BeanWrapper instanceWrapper = createBeanInstance(beanName, mbd, args);
		final Object bean = instanceWrapper.getWrappedInstance();

		// Allow post-processors to modify the merged bean definition.
		// 2. 合并BeanDefinition  (不创建bean)
		// 查找RootBean中@Autowired注解信息，与xml中的BeanDefinition合并存入mbd
		applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);

		// 3. Initialize the bean instance.
		// 3.1 根据mbd创建innerBean
		populateBean(beanName, mbd, instanceWrapper);
		// 3.2 根据mbd初始化RootBean，包括调用set方法(针对具有public set方法的field)，暴力反射setField(针对@Autowired注解的Field)
		Object exposedObject = initializeBean(beanName, bean, mbd);
		...

		return exposedObject;
	}

	// 2. 
	void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName) {
		for (BeanPostProcessor bp : getBeanPostProcessors()) {
			if (bp instanceof MergedBeanDefinitionPostProcessor) {
				MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
				// 2.
				bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
			}
		}
	}
	
	// 3. 
	// Populate the bean instance in the given BeanWrapper with the property value from the bean definition.
	protected void populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw) {

	}
	

}

```

**2. 合并BeanDefinition  (不创建bean)**

```java

package org.springframework.beans.factory.annotation;
public class AutowiredAnnotationBeanPostProcessor extends ...{
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		// 2.1 遍历targetClass的每个field和method。如果有@Autowired注释，那么返回具体属性，并存入InjectionMetadata；否则返回null
		InjectionMetadata metadata = findAutowiringMetadata(beanName, beanType, null);
		// 2.2 把metadata存入beanDefinition.externallyManagedConfigMembers，以便用来初始化RootBean
		metadata.checkConfigMembers(beanDefinition);
	}


	private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
		...
		// 2.1
		InjectionMetadata  metadata = buildAutowiringMetadata(clazz);
		return metadata;
	}


	// 2.1 
	private InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
		LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
		Class<?> targetClass = clazz;

		do {
			final LinkedList<InjectionMetadata.InjectedElement> currElements = new LinkedList<>();
			ReflectionUtils.doWithLocalFields(targetClass, field -> {  // doWithLocalFields: 遍历targetClass的每个field
				// 2.1.1 如果该field有@Autowired注释，那么返回具体属性，并存入InjectionMetadata；否则返回null
				AnnotationAttributes ann = findAutowiredAnnotation(field);
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
			...
		}
		return new InjectionMetadata(clazz, elements);
	}

	// 2.1.1
	private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
		if (ao.getAnnotations().length > 0) {
			for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
				// 返回当前Field或Method的@Autowired注解属性，没有@Autowired注解则返回null
				// 内部核心函数： ao.getDeclaredAnnotations()，并匹配是否包含@Autowired和@Value
				AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
				if (attributes != null) {
					return attributes;
				}
			}
		}
		return null;
	}

}


'''

## debug技巧

* 设置断点
	* 对于@Autowired注释Field，在AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement内部类的构造函数中设置断点。
	* 在bean构造函数设置断点
	* 在以上代码注释处设置断点

