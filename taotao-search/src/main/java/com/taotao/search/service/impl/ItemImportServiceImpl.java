package com.taotao.search.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.Item;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.ItemImportService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by geek on 2017/7/20.
 */
@Service
public class ItemImportServiceImpl implements ItemImportService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllToSolrIndex() throws Exception {

        //1.get data from database
        List<Item> items = itemMapper.getAllItems();

        //2.create index to solr
        SolrInputDocument doc = new SolrInputDocument();

        for (Item i : items) {
            doc.setField("id", i.getId());
            doc.setField("item_title", i.getTitle());
            doc.setField("item_sell_point", i.getSell_point());
            doc.setField("item_price", i.getPrice());
            doc.setField("item_category_name", i.getCategory_name());
            doc.setField("item_image", i.getImage());
            doc.setField("item_desc", i.getItem_desc());
            solrServer.add(doc);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult importItemToSolrIndex(Long itemId) throws Exception {
        //1.get data from database
        Item items = itemMapper.getItemById(itemId);

        //2.create index to solr
        SolrInputDocument doc = new SolrInputDocument();

        doc.setField("id", items.getId());
        doc.setField("item_title", items.getTitle());
        doc.setField("item_sell_point", items.getSell_point());
        doc.setField("item_price", items.getPrice());
        doc.setField("item_category_name", items.getCategory_name());
        doc.setField("item_image", items.getImage());
        doc.setField("item_desc", items.getItem_desc());
        solrServer.add(doc);
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
