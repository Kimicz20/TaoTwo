package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by geek on 2017/7/24.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${INFO_URL}")
    private String INFO_URL;

    @Value("${DESC_URL}")
    private String DESC_URL;

    @Value("${PARAM_URL}")
    private String PARAM_URL;

    @Override
    public ItemInfo getItemById(Long id) {
        ItemInfo result = null;
        String URL = REST_BASE_URL + INFO_URL + id;
        try {
            String jSon = HttpClientUtil.doGet(URL);

            if (!StringUtils.isEmpty(jSon)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jSon, ItemInfo.class);
                if (taotaoResult.getStatus() == 200) {
                    result = (ItemInfo) taotaoResult.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getItemDescById(Long id) {
        TbItemDesc result = null;
        String URL = REST_BASE_URL + DESC_URL + id;
        try {
            String jSon = HttpClientUtil.doGet(URL);

            if (!StringUtils.isEmpty(jSon)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jSon, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200) {
                    result = (TbItemDesc) taotaoResult.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getItemDesc();
    }

    @Override
    public String getItemParamById(Long id) {
        TbItemParamItem result = null;
        String URL = REST_BASE_URL + PARAM_URL + id;
        try {
            String jSon = HttpClientUtil.doGet(URL);

            if (!StringUtils.isEmpty(jSon)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jSon, TbItemParamItem.class);
                if (taotaoResult.getStatus() == 200) {
                    result = (TbItemParamItem) taotaoResult.getData();
                    String paramData = result.getParamData();
                    //json to html
                    List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for(Map m1:jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for(Map m2:list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                            sb.append("            <td>"+m2.get("v")+"</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //return html
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
