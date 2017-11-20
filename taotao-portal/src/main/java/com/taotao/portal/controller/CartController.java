package com.taotao.portal.controller;

import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/8/3.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/itemId")
    public String carttAddItem(@PathVariable Long itemId,
                               @RequestParam(defaultValue = "1") Integer num,
                               HttpServletRequest request,
                               HttpServletResponse response){
        cartService.addCartItem(itemId, num, request, response);
        return "cartSuccess";
    }
}
