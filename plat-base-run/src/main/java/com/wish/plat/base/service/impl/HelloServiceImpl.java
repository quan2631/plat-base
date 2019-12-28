package com.wish.plat.base.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.wish.plat.base.model.School;
import com.wish.plat.base.model.Student;
import com.wish.plat.base.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

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
    public String uploadFile(List<File> files) {
        return "Server receive file size is : " + files.size();
    }
}
