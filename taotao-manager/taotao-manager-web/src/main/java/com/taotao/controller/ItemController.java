package com.taotao.controller;

import com.taotao.pojo.EUIDataGridResult;
import com.taotao.pojo.EUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemService;
import com.taotao.tmp.TbItemParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by geek on 2017/6/11.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/{id}")
    @ResponseBody
    TbItem itemQueryById(@PathVariable Long id) {
        return itemService.ItemQueryById(id);
    }

    /**
     * 商品分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    EUIDataGridResult queryPageItemList(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "30") Integer rows) {
        return itemService.queryPageItemList(page, rows);
    }

    /**
     * 商品类目查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/cat/list")
    @ResponseBody
    List<EUITreeNode> catList(@RequestParam(value = "id",
            defaultValue = "0") Long id) {
        return itemCatService.catList(id);
    }

    /**
     * 添加商品
     *
     * @param tbItem
     * @param desc
     * @param itemParams 商品规格参数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult saveItem(TbItem tbItem, String desc, String itemParams) throws Exception {
        try {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增商品，item = {},desc = {}", tbItem, desc);
            }
            if (StringUtils.isEmpty(tbItem.getTitle())) {
                return TaotaoResult.build(400, "BAD_REQUEST");
            }
            TaotaoResult result = itemService.saveItem(tbItem, desc, itemParams);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增商品成功，itemId = {}", tbItem.getId());
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("新增商品失败！title = " + tbItem.getTitle() + " ,cid = " + tbItem.getCid(), e);
        }
        return TaotaoResult.build(500, "INTERNAL_SERVER_ERROR");
    }

    @RequestMapping("/param/query/itemcatid/{itemcatid}")
    @ResponseBody
    TaotaoResult queryItemParam(@PathVariable Long itemcatid) {
//        return itemService.queryParam(itemcatid);
        return null;
    }

    /**
     * 添加商品规格
     *
     * @param cid
     * @param paramData
     * @return
     */
    @RequestMapping(value = "/param/save/{cid}", method = RequestMethod.POST)
    @ResponseBody
    TaotaoResult saveParam(@PathVariable Long cid, @RequestParam("paramData") String paramData) {
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setItemCatId(cid);
        tbItemParam.setParamData(paramData);
        return itemService.saveParam(tbItemParam);
    }
}
