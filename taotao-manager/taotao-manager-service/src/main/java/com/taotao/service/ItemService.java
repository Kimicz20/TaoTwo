package com.taotao.service;

import com.github.pagehelper.PageInfo;
import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.tmp.TbItemParam;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by geek on 2017/6/11.
 */
@Service
public class ItemService extends BaseService<TbItem> {


    //    @Autowired
//    TbItemParamMapper tbItemParamMapper;
//
    @Autowired
    private ItemDescService itemDescService;
//
//    @Autowired
//    TbItemParamItemMapper tbItemParamItemMapper;



    /**
     * 通过id 查询商品
     *
     * @param id
     * @return
     */
    public TbItem ItemQueryById(long id) {
        return this.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    public EUIDataGridResult queryPageItemList(int page, int rows) {

        //分页查询
        Example example = new Example(TbItem.class);
        example.setOrderByClause("updated DESC");
        PageInfo<TbItem> info = this.queryPageListByExample(page, rows, example);

        //结果封装
        EUIDataGridResult result = new EUIDataGridResult();
        result.setTotal(info.getTotal());
        result.setRows(info.getList());

        return result;
    }

    public TaotaoResult saveItem(TbItem tbItem, String desc, String itemParams) throws Exception {

        //补全信息
        Long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        this.save(tbItem);

        //描述信息
        TaotaoResult result = itemDescService.save(id, desc);

        if (result.getStatus() != 200) {
            throw new Exception();
        }

        //添加商品规格参数
//        result = insertTbItemParamItem(id, itemParams);
//        if (result.getStatus() != 200) {
//            throw new Exception();
//        }

        //同步到solr索引中
//        String r = HttpClientUtil.doGet(SOLR_BASE_URL + id);
//        if (r == null) {
//            return TaotaoResult.build(400, "同步失败");
//        }
        return TaotaoResult.ok();
    }

    /**
     * 添加商品规格参数
     *
     * @param id
     * @param itemParams
     * @return
     */
    public TaotaoResult insertTbItemParamItem(long id, String itemParams) {
//        TbItemParamItem item = new TbItemParamItem();
//        item.setItemId(id);
//        item.setParamData(itemParams);
//        item.setCreated(new Date());
//        item.setUpdated(new Date());
//        tbItemParamItemMapper.insert(item);
        return TaotaoResult.ok();
    }


//    public TaotaoResult queryParam(long id) {
//        TbItemParamExample tbItemParamExample = new TbItemParamExample();
//        TbItemParamExample.Criteria criteria =  tbItemParamExample.createCriteria();
//        criteria.andItemCatIdEqualTo(id);
//        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
//        //查询到结果
//        if(list != null && list.size() >0){
//            return TaotaoResult.ok(list.get(0));
//        }
//        return TaotaoResult.ok();
//    }

    /**
     * 添加商品类目参数
     *
     * @param tbItemParam
     * @return
     */
    public TaotaoResult saveParam(TbItemParam tbItemParam) {

//        tbItemParam.setCreated(new Date());
//        tbItemParam.setUpdated(new Date());
//        tbItemParamMapper.insert(tbItemParam);
        return TaotaoResult.ok();
    }
}
