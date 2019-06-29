package com.ronglian.kangrui.saas.research.common.entity;

import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class IdEntity implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true,dialect = IdentityDialect.MYSQL)
    @Column(insertable = false,updatable = false)
    public Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
