package com.taotao.portal.interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import com.taotao.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by geek on 2017/7/26.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o) throws Exception {
        //1.get token
        String value = CookieUtils.getCookieValue(request, userService.SSO_COOKIE);
        if(StringUtils.isBlank(value)){
            //3.not exit user return false and redirect
            response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_LOGIN_URL
                    +"?redirect="+request.getRequestURL());
            return false;
        }
        //2.get userInfo by token
        TbUser user = userService.getUserInfoByToken(value);
        if(null == user){
            //3.not exit user return false and redirect
            response.sendRedirect(userService.SSO_BASE_URL+userService.SSO_LOGIN_URL
                    +"?redirect="+request.getRequestURL());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
