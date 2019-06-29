package com.ronglian.kangrui.saas.research.sci;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
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
@EnableTransactionManagement
@ComponentScan("com.ronglian.kangrui.saas.research")
@EnableDiscoveryClient
public class ScientificBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ScientificBootstrap.class).web(true).run(args);    }
}
