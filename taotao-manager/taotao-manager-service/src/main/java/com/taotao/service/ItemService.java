package com.taotao.service;

import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;

/**
 * Created by geek on 2017/6/11.
 */
public interface ItemService {

    //根据ID查找指定商品
    TbItem ItemQueryById(long id);

    //根据分页信息返回商品列表
    EUIDataGridResult getPageList(int page,int rows);

    //添加商品
    TaotaoResult insertTbItem(TbItem tbItem,String desc,String itemParams) throws Exception;

    //显示参数模板
    TaotaoResult queryParam(long id);

    //添加模板
    TaotaoResult insertParam(TbItemParam tbItemParam);
}
