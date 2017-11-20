package com.taotao.search.service;

import com.taotao.pojo.TaotaoResult;

/**
 * Created by geek on 2017/7/20.
 */
public interface ItemImportService {

    TaotaoResult importAllToSolrIndex() throws Exception;
    TaotaoResult importItemToSolrIndex(Long itemId) throws Exception;
}
