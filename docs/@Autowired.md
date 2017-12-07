## Introduction

## ʾ������





## ��Ҫ���̣�

1. get bean class
1. ʵ����Bean: createBeanInstance(beanName, mbd, args)
1. �ϲ�/ע��bean������@Autowired��applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
	1. 
	1. 
1. ��ʼ��Bean: initializeBean(beanName, exposedObject, mbd)

## Դ�������

	package org.springframework.beans.factory.support;
	public abstract class AbstractAutowireCapableBeanFactory extends ...{
		...
		/**
		 * Central method of this class: creates a bean instance,
		 * populates the bean instance, applies post-processors, etc.
		 * mbd: xml�и�bean����������
		 * @see #doCreateBean
		 */
		Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args){
			// Instantiate the bean�����÷�����ù��캯��
			BeanWrapper instanceWrapper = createBeanInstance(beanName, mbd, args);
			final Object bean = instanceWrapper.getWrappedInstance();
			
			// Allow post-processors to modify the merged bean definition.
			// �ϲ�����BeanDefinition������@Autowiredע�͵��ڲ�bean
			applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
			
			// Initialize the bean instance.
			populateBean(beanName, mbd, instanceWrapper);  // �����create
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
					AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);  //�����field��@Autowiredע�ͣ���ô����{required=true}�����򷵻�null
					if (attributes != null) {
						return attributes;
					}
				}
			}
			return null;
		}
		
	}


##debug����
����@Autowiredע��Field����AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement�ڲ���Ĺ��캯�������öϵ㡣
