package hello;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @SpringBootApplication 扫描包的默认范围为启动类所在包和子包
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @Bean 注解用于告诉方法，产生一个Bean对象，然后这个Bean对象交给Spring管理。
     * 产生这个Bean对象的方法Spring只会调用一次，随后这个Spring将会将这个Bean对象放在自己的IOC容器中。
     *
     * CommandLineRunner类型是干嘛的？
     *
     * SpringBoot默认启动了这么多bean？是干嘛的？跟spring的区别是？spring的context里没这么多bean啊，只有配置文件里的bean
     * application，applicationTaskExecutor
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
