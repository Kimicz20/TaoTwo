package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by geek on 2017/7/24.
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    @Qualifier("jedisClientCluster")
    private JedisClient jedis;

    @Value("${REDIS_ITEM_BASE_KEY}")
    private String REDIS_ITEM_BASE_KEY;

    @Value("${TIME_EXPIRE}")
    private int TIME_EXPIRE;

    @Override
    public TaotaoResult queryItemInfo(Long itemId) {

        TbItem item  = null;
        //1.search in redis
        try {
            String result = jedis.get(REDIS_ITEM_BASE_KEY+":"+itemId+":base");
            if(!StringUtils.isEmpty(result)){
                item =  JsonUtils.jsonToPojo(result,TbItem.class);
                return TaotaoResult.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.search in db
        item  = itemMapper.selectByPrimaryKey(itemId);

        //3.put in redis
        try {
            jedis.set(REDIS_ITEM_BASE_KEY+":"+itemId+":base",JsonUtils.objectToJson(item));
            jedis.expire(REDIS_ITEM_BASE_KEY+":"+itemId+":base",TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }

    @Override
    public TaotaoResult queryItemDesc(Long itemId) {
        TbItemDesc tbItemDesc  = null;
        //1.search in redis
        try {
            String result = jedis.get(REDIS_ITEM_BASE_KEY+":"+itemId+":desc");
            if(!StringUtils.isEmpty(result)){
                tbItemDesc =  JsonUtils.jsonToPojo(result,TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.search in db
        tbItemDesc  = tbItemDescMapper.selectByPrimaryKey(itemId);

        //3.put in redis
        try {
            jedis.set(REDIS_ITEM_BASE_KEY+":"+itemId+":desc",JsonUtils.objectToJson(tbItemDesc));
            jedis.expire(REDIS_ITEM_BASE_KEY+":"+itemId+":desc",TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(tbItemDesc);
    }

    @Override
    public TaotaoResult queryItemParam(Long itemId) {

        TbItemParamItem tbItemParamItem = null;
        //1.search in redis
        try {
            String result = jedis.get(REDIS_ITEM_BASE_KEY+":"+itemId+":param");
            if(!StringUtils.isEmpty(result)){
                tbItemParamItem =  JsonUtils.jsonToPojo(result,TbItemParamItem.class);
                return TaotaoResult.ok(tbItemParamItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list !=null && list.size() >0){
            tbItemParamItem = list.get(0);
            //put in redis
            try {
                jedis.set(REDIS_ITEM_BASE_KEY+":"+itemId+":param",JsonUtils.objectToJson(tbItemParamItem));
                jedis.expire(REDIS_ITEM_BASE_KEY+":"+itemId+":param",TIME_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return TaotaoResult.ok(tbItemParamItem);
        }
        return TaotaoResult.build(400,"查询无此商品");
    }
}
