package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by geek on 2017/6/29.
 */
public interface ContentService {

    TaotaoResult queryContent(long categoryId);
}
