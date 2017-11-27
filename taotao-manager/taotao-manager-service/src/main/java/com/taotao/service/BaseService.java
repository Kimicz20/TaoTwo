package com.taotao.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.pojo.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by geek on 2017/11/23.
 */
public abstract class BaseService<T extends BasePojo> {


    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public T queryById(Long id){
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<T> queryAll(){
        return mapper.select(null);
    }

    /**
     * 查询一条数据
     * @param t
     * @return
     */
    public T queryOne(T t){
        return mapper.selectOne(t);
    }

    /**
     * 查询所有指定条件数据
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record){
        return mapper.select(record);
    }

    /**
     * 分页条件查询
     * @param page 页号
     * @param rows 每页条数
     * @param record 条件
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows,T record){
        //设置分页属性
        PageHelper.startPage(page,rows);
        return new PageInfo<T>(this.queryListByWhere(record));
    }

    /**
     * 新增数据 返回成功条数
     * @param record
     * @return
     */
    public Integer save(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return mapper.insert(record);
    }

    /**
     * 新增数据 使用不为空的字段
     * @param record
     * @return
     */
    public Integer saveSelective(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return mapper.insertSelective(record);
    }

    /**
     * 修改数据 返回成功条数
     * @param record
     * @return
     */
    public Integer update(T record){
        record.setUpdated(record.getCreated());
        return mapper.updateByPrimaryKey(record);
    }

    /**
     * 修改数据 使用不为空的字段
     * @param record
     * @return
     */
    public Integer updateSelective(T record){
        record.setUpdated(record.getCreated());
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 按照ID删除数据
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * 按照关键字批量删除
     * @param clazz 删除类型
     * @param property 关键字名称
     * @param vaules 关键字列表数据
     * @return
     */
    public Integer deleteByIds(Class clazz, String property, List<Object> vaules){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property,vaules);
        return mapper.deleteByExample(example);
    }

    /**
     * 按照特定条件删除
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record){
        return mapper.delete(record);
    }
}
