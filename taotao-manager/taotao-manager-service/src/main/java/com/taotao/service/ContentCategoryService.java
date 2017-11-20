package com.taotao.service;

import com.taotao.pojo.EUITreeNode;
import com.taotao.pojo.TaotaoResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by geek on 2017/6/28.
 */
public interface ContentCategoryService {

    List<EUITreeNode> listContentCategory(long parentId);

    TaotaoResult insertContentCategory(long parentId,String name);

    TaotaoResult deleteContentCategory(long id);

    TaotaoResult updateContentCategory(long id,String name);
}
