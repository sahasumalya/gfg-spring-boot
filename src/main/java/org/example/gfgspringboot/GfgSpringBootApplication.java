package org.example.gfgspringboot;

import org.example.gfgspringboot.configurations.DemoConfiguration;
import org.example.gfgspringboot.models.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
//@EnableDiscoveryClient
@SpringBootApplication
public class GfgSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GfgSpringBootApplication.class, args);
        //DemoConfiguration demoConfiguration = (DemoConfiguration)applicationContext.getBean("DemoConfiguration");
        /*System.out.println(applicationContext.getBean(Student.class));
        System.out.println(applicationContext.getBean(Student.class));
        System.out.println(applicationContext.getBean(Student.class));
        /*DemoConfiguration demoConfiguration = applicationContext.getBean(DemoConfiguration.class);
        System.out.println(demoConfiguration.getStudent());
        System.out.println(demoConfiguration.getStudent());
        System.out.println(demoConfiguration.getStudent());*/
    }

}
