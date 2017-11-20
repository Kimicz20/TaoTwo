package com.taotao.service;

import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by geek on 2017/6/28.
 */
public interface ContentService {

    TaotaoResult saveContent(TbContent content);

    EUIDataGridResult queryContentList(Long categoryId, Integer page, Integer rows);

    TaotaoResult editContent(TbContent tbContent);

    TaotaoResult deleteContent(String ids);

    void syncRedisCache(Long gategoryId);
}
