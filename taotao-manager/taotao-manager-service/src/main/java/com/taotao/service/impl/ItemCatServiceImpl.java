package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.EUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2017/6/12.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EUITreeNode> getCatList(long id) {
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        List<EUITreeNode> result = new ArrayList<>();
        for(TbItemCat t :list){
            EUITreeNode e = new EUITreeNode();
            e.setId(t.getId());
            e.setText(t.getName());
            e.setState(t.getIsParent()?"closed":"open");
            result.add(e);
        }
        return result;
    }
}
