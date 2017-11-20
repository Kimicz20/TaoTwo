package com.taotao.portal.service;

import com.taotao.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/8/3.
 */
public interface CartService {
    TaotaoResult addCartItem(Long itemId, Integer num, HttpServletRequest request,HttpServletResponse response);
}
