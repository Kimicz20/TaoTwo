package com.taotao.sso.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/7/25.
 */
public interface UserService {

    TaotaoResult checkData(String param,Integer type);
    TaotaoResult createUser(TbUser user);
    TaotaoResult loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserInfoByToken(String token);
    TaotaoResult logout(String token);
}
