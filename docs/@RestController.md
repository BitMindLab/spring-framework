

@RestController is a stereotype annotation that combines 
@ResponseBody and @Controller.

返回页面用@Controller，要想返回数据就用@RestController,
这个注解对于返回数据比较方便，因为它会自动将对象实体转换为JSON格式。


1)如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，
配置的视图解析器InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。