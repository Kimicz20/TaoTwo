package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by geek on 2017/7/26.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_COOKIE}")
    public String SSO_COOKIE;

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_USER_INFO}")
    private String SSO_USER_INFO;

    @Value("${SSO_LOGIN_URL}")
    public String SSO_LOGIN_URL;
    @Override
    public TbUser getUserInfoByToken(String token) {
        String get = null;
        try {
            get = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_INFO + token);
            TaotaoResult result = TaotaoResult.formatToPojo(get,TbUser.class);
            if(result.getStatus() == 200){
                return (TbUser) result.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
