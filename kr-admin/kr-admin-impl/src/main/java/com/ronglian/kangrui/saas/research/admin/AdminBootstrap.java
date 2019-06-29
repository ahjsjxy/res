package com.ronglian.kangrui.saas.research.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-05-25 12:44
 */
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients({"com.ronglian.kangrui.saas.research"})
@EnableScheduling
@ServletComponentScan("com.ronglian.kangrui.saas.research.admin.config.druid")
@EnableTransactionManagement
@ComponentScan("com.ronglian.kangrui.saas.research")
@EnableDiscoveryClient
public class AdminBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminBootstrap.class).web(true).run(args);    }
}
