package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 * Created by geek on 2017/7/24.
 */
public interface ItemService {

    TaotaoResult queryItemInfo(Long itemId);
    TaotaoResult queryItemDesc(Long itemId);
    TaotaoResult queryItemParam(Long itemId);
}
