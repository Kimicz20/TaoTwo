package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geek on 2017/8/3.
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${INFO_URL}")
    private String INFO_URL;

    @Override
    public TaotaoResult addCartItem(Long itemId, Integer num,
                                    HttpServletRequest request, HttpServletResponse response) {

        CartItem cartItem =  null;

        //get CartItem from Cookie
        List<CartItem> cartList = getCartList(request);
        for (CartItem item:cartList){
            if(item.getId() == itemId){
                item.setNum(item.getNum()+num);
                cartItem = item;
                break;
            }
        }

        //else search in db
        if(cartItem == null){
            cartItem = new CartItem();
            //rest search
            String url = REST_BASE_URL+INFO_URL+itemId;
            try {
                String itemJson = HttpClientUtil.doGet(url);
                if(!StringUtils.isBlank(itemJson)){
                    TaotaoResult result = TaotaoResult.formatToPojo(itemJson, TbItem.class);
                    if(result.getStatus() == 200){
                        TbItem tbItem = (TbItem) result.getData();
                        //transfer data to CartItem
                        cartItem.setId(tbItem.getId());
                        cartItem.setNum(num);
                        cartItem.setTitle(tbItem.getTitle());
                        cartItem.setPrice(tbItem.getPrice());
                        cartItem.setImage(StringUtils.isBlank(tbItem.getImage())?"":tbItem.getImage().split(",")[0]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
            }
        }

        //put list to Cookie
        cartList.add(cartItem);
        try {
            CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartList),true);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    private List<CartItem> getCartList(HttpServletRequest request){

        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
        if(StringUtils.isBlank(cartJson)){
            return new ArrayList<>();
        }
        try {
            List<CartItem> cartItems = JsonUtils.jsonToList(cartJson, CartItem.class);
            return cartItems;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
