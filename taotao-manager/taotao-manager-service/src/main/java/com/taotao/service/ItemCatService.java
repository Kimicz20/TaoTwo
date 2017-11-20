package com.taotao.service;

import com.taotao.pojo.EUITreeNode;

import java.util.List;

/**
 * Created by geek on 2017/6/12.
 */

public interface ItemCatService {
    List<EUITreeNode> getCatList(long id);
}
