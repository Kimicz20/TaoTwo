package com.taotao.service;

import com.taotao.pojo.EUITreeNode;
import com.taotao.pojo.TbItemCat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2017/11/24.
 */
@Service
public class ItemCatService extends BaseService<TbItemCat> {

    /**
     * 返回所有商品类目
     * @param id
     * @return
     */
    public List<EUITreeNode> catList(Long id){
        TbItemCat record = new TbItemCat();
        record.setParentId(id);
        List<TbItemCat> list = this.queryListByWhere(record);
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
