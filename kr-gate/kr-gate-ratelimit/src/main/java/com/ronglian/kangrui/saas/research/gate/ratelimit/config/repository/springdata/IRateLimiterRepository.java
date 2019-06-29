package com.ronglian.kangrui.saas.research.gate.ratelimit.config.repository.springdata;

import com.ronglian.kangrui.saas.research.gate.ratelimit.config.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateLimiterRepository extends CrudRepository<Rate, String> {

}
