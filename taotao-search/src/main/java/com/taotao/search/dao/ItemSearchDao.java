package com.taotao.search.dao;

import com.taotao.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by geek on 2017/7/20.
 */
public interface ItemSearchDao {
    SearchResult search(SolrQuery solrQuery) throws Exception;
}
