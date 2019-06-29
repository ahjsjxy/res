package com.ronglian.kangrui.saas.research.shirobase.redis;

import java.io.Serializable;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ronglian.kangrui.saas.research.commonrbac.redis.RedisClient;
import com.ronglian.kangrui.saas.research.commonrbac.utils.Constants;
import com.ronglian.kangrui.saas.research.commonrbac.utils.SerializeUtil;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    @Autowired
    private RedisClient redisService;

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        
        log.info("创建Session: {}", sessionId.toString());
        
        redisService.set(Constants.SESSION_PREFIX + session.getId(), SerializeUtil.serialize(session), session.getTimeout() / 1000);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        byte[] value = redisService.get(Constants.SESSION_PREFIX + serializable);
        Session s = SerializeUtil.deserialize(value, Session.class);
        if(s == null)
            throw new AuthorizationException("用户认证失败");

        log.info("读取Session: {}", s.getId().toString());
        return s;
    }


    @Override
    protected void doUpdate(Session session) {
        if (session == null || session.getId() == null) {
            throw new AuthorizationException("用户会话已关闭");
        }
        
        log.info("更新Session: {}", session.getId().toString());
        
        super.doUpdate(session);
        redisService.set(Constants.SESSION_PREFIX + session.getId(), SerializeUtil.serialize(session),session.getTimeout() / 1000);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            throw new AuthorizationException("用户会话已关闭");
        }
        
        log.info("删除Session: {}", session.getId().toString());
        
        super.doDelete(session);
        redisService.delete(Constants.SESSION_PREFIX + session.getId());
    }


}