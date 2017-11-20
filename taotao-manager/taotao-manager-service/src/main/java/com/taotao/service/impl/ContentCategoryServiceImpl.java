package com.taotao.service.impl;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.*;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by geek on 2017/6/28.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUITreeNode> listContentCategory(long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUITreeNode> resultList = new ArrayList<>();
        for(TbContentCategory content:list){
            EUITreeNode node = new EUITreeNode();
            node.setId(content.getId());
            node.setText(content.getName());
            node.setState(content.getIsParent()?"closed":"open");

            resultList.add(node);
        }
        return resultList;
    }

    /**
     * 插入一个内容分类
     * @param parentId
     * @param name
     * @return 返回当前节点的ID
     */
    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {

        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);

        //补全信息
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        //插入数据库
        contentCategoryMapper.insert(contentCategory);

        //父节点信息更新
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentCat.getIsParent()){
            parentCat.setIsParent(true);
            //更新
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回新增节点的ID
        return TaotaoResult.ok(contentCategory);
    }

    /**
     * 删除内容节点
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteContentCategory(long id) {
        //获取当前节点
        TbContentCategory curCat = contentCategoryMapper.selectByPrimaryKey(id);
        //1.删除的为叶子节点
        if(!curCat.getIsParent()){
            //获取父节点
            TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(curCat.getParentId());
            long pId = parentCat.getId();
            //删除叶子节点
            contentCategoryMapper.deleteByPrimaryKey(id);

            //更新父节点
            TbContentCategoryExample example = new TbContentCategoryExample();
            Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(pId);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            if(list.size() ==0 ){
                parentCat.setIsParent(false);
                //更新
                contentCategoryMapper.updateByPrimaryKey(parentCat);
            }
        }else{
            //2.删除的为父节点

            //获取其所有子节点
            TbContentCategoryExample example = new TbContentCategoryExample();
            Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

            //循环删除
            for (TbContentCategory item:list) {
                deleteContentCategory(item.getId());
            }
            //删除最外层
            deleteContentCategory(id);
        }

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(long id, String name) {
        TbContentCategory curCat = contentCategoryMapper.selectByPrimaryKey(id);
        curCat.setName(name);
        contentCategoryMapper.updateByPrimaryKey(curCat);
        return TaotaoResult.ok();
    }
}
