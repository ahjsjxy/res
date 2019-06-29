package com.ronglian.kangrui.saas.research.common.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * Created by Mr.AG
 * Date: 17/1/13
 * Time: 15:13
 * Version 1.0.0
 */
public abstract class BaseBiz<M extends Mapper<T>, T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    /**
     * 如果超出一条匹配数据，抛出TooManyResultsException异常
     * 如果无匹配，返回NULL
     * @param entity
     * @return
     */
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    /**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     * @param id
     * @return
     */
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    /*插入一条数据 null的属性也会保存
       *支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
       *优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
       *
       **/
    public void insert(T entity) {
        // EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insert(entity);
    }

    /**
     *插入一条数据 null的属性不会保存
     *只插入不为null的字段,不会影响有默认值的字段
     *支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     *优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     */
    public T insertSelective(T entity) {
        // EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insertSelective(entity);
        return entity;
    }

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     * @param entity
     * @return
     */
    public void delete(T entity) {
        mapper.delete(entity);
    }

    /**
     *通过主键进行删除,这里最多只会删除一条数据
     *单个字段做主键时,可以直接写主键的值
     *联合主键时,key可以是实体类,也可以是Map
     *
     */
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id批量删除
     * @param ids
     * @return
     */
    public void deleteBatchIds(String[] ids){
        if(ids!=null && ids.length>0){
            List<String> idList= Arrays.asList(ids);
            for(String id:idList){
                mapper.deleteByPrimaryKey(Long.valueOf(id));
            }
        }
    }

    /**
     * 根据主键进行更新,这里最多只会更新一条数据
     * 参数为实体类
     */
    public void updateById(T entity) {
        // EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键进行更新
     * 只会更新不是null的数据
     */
    public void updateSelectiveById(T entity) {
        // EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }


    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public TableResultResponse<T> selectByQuery(Query query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        if(query.entrySet().size()>0) {
            Example.Criteria criteria = example.createCriteria();
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new TableResultResponse<T>(result.getTotal(), list);
    }

//    public T saveOrUpdate (T entity){
//        if(entity.getId() !=null){
//            this.updateById(entity);
//        }else{
//            entity = this.insertSelective(entity);
//        }
//        return entity;
//    }


}
