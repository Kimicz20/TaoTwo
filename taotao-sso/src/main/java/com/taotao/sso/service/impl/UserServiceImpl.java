package com.taotao.sso.service.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by geek on 2017/7/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    @Qualifier("jedisClientCluster")
    private JedisClient jedisClient;

    @Value("${REDIS_SSO_USER_KEY}")
    private String REDIS_SSO_USER_KEY;

    @Value("${SSO_TIME_EXPIRE}")
    private int SSO_TIME_EXPIRE;

    @Value("${SSO_COOKIE}")
    private String SSO_COOKIE;

    /**
     * check different type of param is success
     * @param param to check data
     * @param type different type
     *             1、2、3:username、phone、email
     * @return
     */
    @Override
    public TaotaoResult checkData(String param, Integer type) {

        //1. create query criteria
        TbUserExample userExample = new TbUserExample();
        Criteria criteria = userExample.createCriteria();

        //2.set Criteria
        if(1 == type){
            criteria.andUsernameEqualTo(param);
        }
        if(2 == type){
            criteria.andPhoneEqualTo(param);
        }
        if(3 == type){
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> tbUsers = userMapper.selectByExample(userExample);
        if(tbUsers == null || tbUsers.size()==0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {

        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        int insert = userMapper.insert(user);
        if(insert ==0 )
            return TaotaoResult.build(400,"注册失败. 请校验数据后请再提交数据.");
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult loginUser(String username, String password,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        //1. create query criteria
        TbUserExample userExample = new TbUserExample();
        Criteria criteria = userExample.createCriteria();
        try {
            username = new String(username.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        criteria.andUsernameEqualTo(username);

        List<TbUser> tbUsers = userMapper.selectByExample(userExample);
        if (tbUsers == null || tbUsers.size() ==0){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser user =  tbUsers.get(0);
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!s.equals(user.getPassword())){
            return TaotaoResult.build(400,"用户名或密码错误");
        }

        //create token
        String token = UUID.randomUUID().toString();
        user.setPassword(null);

        jedisClient.set(REDIS_SSO_USER_KEY+":"+token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_SSO_USER_KEY+":"+token,SSO_TIME_EXPIRE);

        //put token to cookie
        CookieUtils.setCookie(request,response,SSO_COOKIE, token);

        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserInfoByToken(String token) {

        // get user info from redis by token
        String user = jedisClient.get(REDIS_SSO_USER_KEY + ":" + token);
        if (StringUtils.isBlank(user)){
            return TaotaoResult.build(400,"用户session已经过期");
        }
        //uodate key's expire
        jedisClient.expire(REDIS_SSO_USER_KEY+":"+token,SSO_TIME_EXPIRE);
        return TaotaoResult.ok(JsonUtils.jsonToPojo(user,TbUser.class));
    }

    @Override
    public TaotaoResult logout(String token) {

        // get user info from redis by token
        String user = jedisClient.get(REDIS_SSO_USER_KEY + ":" + token);
        if (StringUtils.isBlank(user)){
            return TaotaoResult.ok();
        }

        //delete token
        try {
            jedisClient.del(REDIS_SSO_USER_KEY + ":" + token);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(400,"注销失败");
        }
        return TaotaoResult.ok();
    }
}
