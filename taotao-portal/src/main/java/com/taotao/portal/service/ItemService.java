package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

/**
 * Created by geek on 2017/7/24.
 */
public interface ItemService {

    ItemInfo getItemById(Long id);

    String getItemDescById(Long id);

    String getItemParamById(Long id);
}
