package com.taotao.rest.service;

import com.taotao.rest.pojo.ItemCatsResult;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/6/28.
 */
public interface ItemCatService {

    //获取右侧列表
    ItemCatsResult getItemCatList();
}
