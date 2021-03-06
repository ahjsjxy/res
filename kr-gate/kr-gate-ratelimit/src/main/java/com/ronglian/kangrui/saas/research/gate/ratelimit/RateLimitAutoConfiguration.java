/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ronglian.kangrui.saas.research.gate.ratelimit;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.IUserPrincipal;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.properties.RateLimitProperties;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.repository.InMemoryRateLimiter;
import com.ronglian.kangrui.saas.research.gate.ratelimit.filters.RateLimitFilter;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.repository.springdata.IRateLimiterRepository;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.repository.springdata.SpringDataRateLimiter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ronglian.kangrui.saas.research.gate.ratelimit.config.RateLimiter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnProperty(prefix = RateLimitProperties.PREFIX, name = "enabled", havingValue = "true")
public class RateLimitAutoConfiguration {

    @Bean
    public RateLimitFilter rateLimiterFilter(final RateLimiter rateLimiter,
                                             final RateLimitProperties rateLimitProperties,
                                             final RouteLocator routeLocator, final IUserPrincipal userPrincipal) {
        return new RateLimitFilter(rateLimiter, rateLimitProperties, routeLocator,userPrincipal);
    }

    @EntityScan
    @EnableJpaRepositories
    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.PREFIX, name = "repository", havingValue = "JPA")
    public static class SpringDataConfiguration {

        @Bean
        public RateLimiter springDataRateLimiter(IRateLimiterRepository rateLimiterRepository) {
            return new SpringDataRateLimiter(rateLimiterRepository);
        }

    }

    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(prefix = RateLimitProperties.PREFIX, name = "repository", havingValue = "IN_MEMORY", matchIfMissing = true)
    public static class InMemoryConfiguration {

        @Bean
        public RateLimiter inMemoryRateLimiter() {
            return new InMemoryRateLimiter();
        }
    }

}

