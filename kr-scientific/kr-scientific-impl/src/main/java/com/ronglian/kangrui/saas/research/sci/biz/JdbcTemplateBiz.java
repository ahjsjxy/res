package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.sci.util.MysqlSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JdbcTemplateBiz {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryForList(String sql){
        return jdbcTemplate.queryForList(sql);
    }


    public Long  insert(String sql,Map<String, Object> fields){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
                        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        Set<String> fieldNames = fields.keySet();
                        int i = 0;
                        for (String fieldName : fieldNames) {
                            if(fields.get(fieldName) != null ){
                                if(fieldName.equals(MysqlSqlUtil.idColumn)){
                                    continue;
                                }
                                i ++ ;
                                ps.setString(i,fields.get(fieldName).toString());
                            }
                        }
                        return ps;
                    }
                }, keyHolder);
        return Long.parseLong(keyHolder.getKey()+"");
    }



    public void  update(String sql,Map<String, Object> fields){
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
                        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        Set<String> fieldNames = fields.keySet();
                        int i = 0;
                        for (String fieldName : fieldNames) {
                            if(fields.get(fieldName) != null ){
                                if(fieldName.equals(MysqlSqlUtil.idColumn)){
                                    continue;
                                }
                                i ++ ;
                                ps.setString(i,fields.get(fieldName).toString());
                            }
                        }
                        return ps;
                    }
                });
    }



    public void  updateDel(String sql){
        jdbcTemplate.update(sql);
    }


    public void  execute(String sql){
        jdbcTemplate.execute(sql);
    }


}
