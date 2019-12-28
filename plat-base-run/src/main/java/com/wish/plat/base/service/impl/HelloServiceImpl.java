package com.wish.plat.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.wish.plat.base.model.School;
import com.wish.plat.base.model.Student;
import com.wish.plat.base.service.HelloService;
import com.wish.plat.common.PlatData;
import com.wish.plat.common.PlatRequestBody;
import com.wish.plat.common.PlatResponseBody;
import com.wish.plat.util.token.JwtUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: QUAN
 * @date: Created in 2019/7/30 11:37
 * @description:
 * @modified By:
 */
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "rest"),@SofaServiceBinding(bindingType = "bolt")})
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        log.info("Server receive1: " + name);
        log.info("Server receive2: " + name);
        log.info("Server receive3: " + name);
        return "hello " + name + "!";
    }

    @Override
    public String sayHelloStr(String id, String code, String name) {
        System.out.println("Server receive: " + id);
        System.out.println("Server receive: " + code);
        System.out.println("Server receive: " + name);
        return "hello " + id + "!";
    }

    @Override
    public String sayHelloObj(School school) {
        return "hello " + school + "!";
    }

    @Override
    public String sayHelloNestedObj(Student student) {
        return "hello " + student + "!";
    }

    @Override
    public String sayHelloObjH(School school)  {
        return "post sayHelloObjH:" + school + "!";
    }

    @Override
    public String sayHelloNestedObjH(Student student)  {
        return "post sayHelloNestedObjH:" + student + "!";
    }

    @Override
    public PlatResponseBody standMethodSofa(PlatRequestBody requestBody) {
        StringBuilder msg = new StringBuilder();
        /**
         * 1、获取请求时的参数
         */
        PlatData data = requestBody.getData();
        Map<String, Map<String, Object>> params = data.getPlat();
        Map<String, Object> loginMap = params.get("login");
        String loginId = loginMap.get("loginId").toString();
        msg.append("loginId is : " ).append(loginId);
        /**
         * 2、获取网关程序拦截token后解密的用户信息
         */
        Map<String, Object> userInfoMap = params.get("userInfo");
        if(null != userInfoMap){
            String userInfoStr = JSON.toJSONString(userInfoMap);
            JwtUserDetail userDetail = JSON.parseObject(userInfoStr, JwtUserDetail.class);
            msg.append(", cityId is : ").append(userDetail.getCityID());
        }

        /**
         * 3、设置返回结果并返回
         */
        PlatResponseBody ret = new PlatResponseBody();
        ret.setCode("1");
        ret.setMsg(msg.toString());
        return ret;
    }

    @Override
    public PlatResponseBody standMethodHttp(PlatRequestBody requestBody) {
        return this.standMethodSofa(requestBody);
    }

    @Override
    public String uploadFile(List<File> files) {
        return "Server receive file size is : " + files.size();
    }
}
