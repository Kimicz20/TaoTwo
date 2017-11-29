package com.taotao.portal.service;

import com.taotao.tmp.TbUser;

/**
 * Created by geek on 2017/7/26.
 */
public interface UserService {
    TbUser getUserInfoByToken(String token);
}
