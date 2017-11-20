package com.taotao.portal.service;

import com.taotao.pojo.SearchResult;

/**
 * Created by geek on 2017/7/22.
 */
public interface SearchService {

    SearchResult search(String condition,String page);

}
