package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.pojo.TbItemDescExample.Criteria;
import com.taotao.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by geek on 2017/6/11.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemMapper itemMapper;

    @Autowired
    TbItemParamMapper tbItemParamMapper;

    @Autowired
    TbItemDescMapper tbItemDescMapper;

    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL;

    @Override
    public TbItem ItemQueryById(long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public EUIDataGridResult getPageList(int page, int rows) {

        TbItemExample example =  new TbItemExample();
        // 分页
        PageHelper.startPage(page,rows);

        List<TbItem> list = itemMapper.selectByExample(example);

        PageInfo<TbItem> info = new PageInfo<TbItem>(list);

        EUIDataGridResult result =  new EUIDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(list);

        return result;
    }

    @Override
    public TaotaoResult insertTbItem(TbItem tbItem, String desc, String itemParams) throws Exception {
        //补全信息
        Long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        itemMapper.insert(tbItem);

        //描述信息
        TaotaoResult result = insertTbItemDesc(id,desc);

        if (result.getStatus() != 200){
            throw new Exception();
        }

        //添加商品规格参数
        result = insertTbItemParamItem(id,itemParams);
        if (result.getStatus() != 200){
            throw new Exception();
        }

        //同步到solr索引中
        String r = HttpClientUtil.doGet(SOLR_BASE_URL + id);
        if(r == null){
            return TaotaoResult.build(400,"同步失败");
        }
        return TaotaoResult.ok();
    }

    /**
     * 添加商品规格参数
     * @param id
     * @param itemParams
     * @return
     */
    public TaotaoResult insertTbItemParamItem(long id,String itemParams) {
        TbItemParamItem item =  new TbItemParamItem();
        item.setItemId(id);
        item.setParamData(itemParams);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        tbItemParamItemMapper.insert(item);
        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述信息
     * @param id
     * @param desc
     * @return
     */
    public TaotaoResult insertTbItemDesc(long id,String desc) {
        TbItemDesc itemDesc =  new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult queryParam(long id) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria =  tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(id);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        //查询到结果
        if(list != null && list.size() >0){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertParam(TbItemParam tbItemParam) {

        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        tbItemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }
}
