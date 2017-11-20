package com.taotao.search.service;

import com.taotao.pojo.SearchResult;

/**
 * Created by geek on 2017/7/20.
 */
public interface ItemSearchService {

    SearchResult itemSearch(String condition, Integer page, Integer rows) throws Exception;

}
