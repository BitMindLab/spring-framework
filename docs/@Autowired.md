## Introduction

## 示例代码





## 主要流程：

1. get bean class
1. 实例化Bean: createBeanInstance(beanName, mbd, args)
1. 合并/注入bean，处理@Autowired：applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
	1. 
	1. 
1. 初始化Bean: initializeBean(beanName, exposedObject, mbd)

## 源码分析：

```java
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
```

##debug技巧
对于@Autowired注释Field，在AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement内部类的构造函数中设置断点。
