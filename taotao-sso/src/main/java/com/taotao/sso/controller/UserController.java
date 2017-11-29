package com.taotao.sso.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.tmp.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/7/25.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //page show
    @RequestMapping("/showLogin")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        String a ="";
        return "login";
    }

    @RequestMapping("/showRegister")
    public String showRegister(){
        return "register";
    }

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){

        TaotaoResult taotaoResult = null;
        //1. verify param and type
        if(StringUtils.isBlank(param)){
            taotaoResult = TaotaoResult.build(400,"检验内容不能够为空");
        }
        if(type == null){
            taotaoResult = TaotaoResult.build(400,"检验内容类型不能够为空");
        }
        if(type != 1 && type != 2 && type != 3){
            taotaoResult = TaotaoResult.build(400,"检验内容类型不正确");
        }

        //2. do check
        if(null == taotaoResult){
            try {
                taotaoResult = userService.checkData(param, type);
            } catch (Exception e) {
                taotaoResult = TaotaoResult.build(500,"校验出错");
            }
        }

        //3.verify error ,JsonP return type
        if(null != callback){
            MappingJacksonValue jacksonValue = new MappingJacksonValue(taotaoResult);
            jacksonValue.setJsonpFunction(callback);
            return jacksonValue;
        }else{
            return taotaoResult;
        }
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        try {
            userService.createUser(user);
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request,
                              HttpServletResponse response){
        try {
           return userService.loginUser(username,password,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserInfoBytoken(@PathVariable String token,String callback){

        TaotaoResult result = null;
        try {
            result =  userService.getUserInfoByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        if(!StringUtils.isBlank(callback)){
            MappingJacksonValue value = new MappingJacksonValue(result);
            value.setJsonpFunction(callback);
            return value;
        }
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpServletRequest request,String callback){

        //get token
        String token = CookieUtils.getCookieValue(request, "SSO_COOKIE");
        if(StringUtils.isBlank(token)){
            return TaotaoResult.build(400,"请先登录");
        }

        //logout
        TaotaoResult result = null;
        try {
            result =  userService.logout(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        //jsonp return
        if(!StringUtils.isBlank(callback)){
            MappingJacksonValue value = new MappingJacksonValue(result);
            value.setJsonpFunction(callback);
            return value;
        }
        return result;
    }
}
