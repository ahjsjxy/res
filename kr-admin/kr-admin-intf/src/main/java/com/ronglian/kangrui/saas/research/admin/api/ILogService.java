package com.ronglian.kangrui.saas.research.admin.api;

import com.ronglian.kangrui.saas.research.admin.vo.log.LogInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-07-01 15:16
 */
@FeignClient("kr-admin")
public interface ILogService {
  @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
  public void saveLog(LogInfo info);
}
