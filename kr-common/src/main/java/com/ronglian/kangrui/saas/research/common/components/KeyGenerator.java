package com.ronglian.kangrui.saas.research.common.components;

import com.ronglian.kangrui.saas.research.common.entity.keygen.impl.IPKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

    @Bean
    public IPKeyGenerator getIPKeyGenerator(){
        return new IPKeyGenerator();
    }
}
