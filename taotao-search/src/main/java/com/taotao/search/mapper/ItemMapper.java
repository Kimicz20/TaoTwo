package com.taotao.search.mapper;

import com.taotao.pojo.Item;

import java.util.List;

/**
 * Created by geek on 2017/7/20.
 */
public interface ItemMapper {

    List<Item> getAllItems();
    Item getItemById(Long id);
}
