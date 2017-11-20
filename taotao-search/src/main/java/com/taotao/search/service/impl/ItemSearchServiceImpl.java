package com.taotao.search.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.pojo.SearchResult;
import com.taotao.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/7/20.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private ItemSearchDao itemSearchDao;

    @Override
    public SearchResult itemSearch(String condition, Integer page, Integer rows) throws Exception {

        //1. new query
        SolrQuery query = new SolrQuery();

        //2. set query
        query.setStart((page-1) * rows);
        query.setRows(rows);

        //2.1 search field
        query.set("df","item_keywords");

        //2.2 set highlight
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");

        //2.3 search condition
        if (StringUtils.isEmpty(condition)){
            query.setQuery("*:*");
        }else{
            query.setQuery(condition);
        }

        SearchResult result = null;


        //2.4 execute search
        result = itemSearchDao.search(query);

        long recordCount = result.getRecordCount();
        long pageCount = recordCount / rows;
        if(recordCount % rows >0){
            pageCount++;
        }
        result.setCurPage(page);
        result.setPageCount(pageCount);

        return result;

    }
}
