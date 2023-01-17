package com.msa.example.chapter01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Slf4j // = private static final Logger log = LoggerFactory.getLogger(Slf4jSample.class);
@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        // SpringApplication의 run() 메소드 실행 시 스프링 빈 컨테이너인 ApplicationContext 객체 리턴해 ctx에 저장
        ConfigurableApplicationContext ctx = SpringApplication.run(MsaProjectApplication.class, args);

        // ApplicationContext의 getBean() 메소드는 인자에 맞는 스프링 빈 객체를 리턴
        // 여기서는 application.properties에 저장된 key-value 값을 확인하기 위해 Environment 객체 리턴
        // ctx.getBean(Environment.class) -> getEnvironment()로 변경 가능
        Environment environment = ctx.getBean(Environment.class);

        // server.port 밸류값을 portValue에 저장
        String portValue = environment.getProperty("server.port");

        // 콘솔에 로그 출력(포트 이름)
        log.info("Customized Port : {}", portValue);

        // ApplicationContext 객체가 관리하는 스프링 빈들의 이름을 String[] 배열로 가져옴
        String[] beanNames = ctx.getBeanDefinitionNames();

        // 콘솔에 로그 출력(빈 이름)
        Arrays.stream(beanNames).forEach(name -> log.info("Bean Name : {}", name));
    }
}
