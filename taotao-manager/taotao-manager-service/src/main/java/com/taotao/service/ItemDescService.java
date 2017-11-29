package com.taotao.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/11/29.
 */
@Service
public class ItemDescService extends BaseService<TbItemDesc> {

    /**
     * 添加商品描述信息
     *
     * @param id
     * @param desc
     * @return
     */
    public TaotaoResult save(long id, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        this.save(itemDesc);
        return TaotaoResult.ok();
    }
}
