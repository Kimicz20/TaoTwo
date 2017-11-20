package com.taotao.portal.service.impl;

import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.SearchService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geek on 2017/7/22.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String condition, String page) {

        Map<String, String> param = new HashMap<>();
        param.put("condition",condition);
        param.put("page",page);

        try {
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (taotaoResult.getStatus() == 200){
                return (SearchResult) taotaoResult.getData();
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
}
