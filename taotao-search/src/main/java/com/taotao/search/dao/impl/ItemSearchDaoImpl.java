package com.taotao.search.dao.impl;

import com.taotao.search.dao.ItemSearchDao;
import com.taotao.pojo.Item;
import com.taotao.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by geek on 2017/7/20.
 */
@Repository
public class ItemSearchDaoImpl implements ItemSearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery solrQuery) throws Exception {

        //1. query from solr
        QueryResponse response = solrServer.query(solrQuery);

        //2.get result
        SolrDocumentList results = response.getResults();

        //3. set searchResult
        List<Item> itemList = new ArrayList<>();
        for(SolrDocument solrDocument: results){

            //3.1 new a item to set
            Item item  = new Item();
            item.setId((String) solrDocument.get("id"));

            //3.2. highlight title , the doc is <id,title,xxx>
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";

            //if has highlight
            if (null != list && !list.isEmpty()) {
                title = list.get(0);
            } else {
                //just get title
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            item.setPrice((Long)solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));

            //5. put item into the result list
            itemList.add(item);
        }
        SearchResult result = new SearchResult();

        result.setRecordCount(results.getNumFound());

        result.setItemList(itemList);

        return result;
    }
}
