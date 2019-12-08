package com.zk.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"com.zk"}, exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = {"com.zk.mp.dao"})
public class RunningApplication {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        new SpringApplicationBuilder(RunningApplication.class)
                .properties("spring.config.name=application,datasource")
                .run(args);
    }

}