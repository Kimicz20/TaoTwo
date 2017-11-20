package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.ADItem;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2017/6/29.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${CONTENT_PATH_URL}")
    private String CONTENT_PATH_URL;

    @Override
    public String getContentList() {

        //1.创建一个HTTPGet请求
        String resultStr = HttpClientUtil.doGet(REST_BASE_URL + CONTENT_PATH_URL );
        //2.格式转换
        TaotaoResult result = TaotaoResult.formatToList(resultStr, TbContent.class);

        List<ADItem> items = new ArrayList<>();
        if(result.getStatus() == 200){
            //获取数据
            List<TbContent> list = (List<TbContent>) result.getData();
            for(TbContent tbContent:list){
                ADItem ad = new ADItem();
                ad.setHeight(240);
                ad.setWidth(800);
                ad.setSrc(tbContent.getPic());
                ad.setHeightB(240);
                ad.setWidth(550);
                ad.setSrcB(tbContent.getPic2());
                ad.setAlt(tbContent.getTitleDesc());
                ad.setHref(tbContent.getUrl());
                items.add(ad);
            }
            return JsonUtils.objectToJson(items);
        }
        return null;
    }
}
