package com.net.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/*@ComponentScan("com.net")
@SpringBootApplication
public class SpringApplicationSimple {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationSimple.class, args);
    }
}*/

@ComponentScan("com.net")
@SpringBootApplication
public class SpringApplicationSimple extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringApplicationSimple.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationSimple.class, args);
    }
}