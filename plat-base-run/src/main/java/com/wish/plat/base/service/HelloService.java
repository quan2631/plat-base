package com.wish.plat.base.service;

import com.wish.plat.base.model.School;
import com.wish.plat.base.model.Student;
import com.wish.plat.common.PlatRequestBody;
import com.wish.plat.common.PlatResponseBody;

import javax.ws.rs.*;
import java.io.File;
import java.util.List;

/**
 * @author: QUAN
 * @date: Created in 2019/7/30 11:37
 * @description:
 * @modified By:
 */
@Path("hello")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface HelloService {
    /**
     * 一个普通参数测试
     * @param name
     * @return
     */
    @GET
    @Path("sayHello/{name}/")
    String sayHello(@PathParam("name") String name);

    /**
     * 多个简单参数测试
     * @param id
     * @param name
     * @param code
     * @return
     */
    @GET
    @Path("sayHello/{id}/{name}/{code}/")
    String sayHelloStr(@PathParam("id") String id, @PathParam("name") String name, @PathParam("code") String code);

    /**
     * 简单对象测试:SOFA
     * @param school
     * @return
     */
    @Path("sayHelloObj")
    String sayHelloObj(School school);

    /**
     * 嵌套对象测试:SOFA
     * @param student
     * @return
     */
    @Path("ayHelloNestedObj")
    String sayHelloNestedObj(Student student);
    /**
     * 简单对象测试:HTTP
     * @param school
     * @return
     */
    @POST
    @Path("httpTest/sayHelloObj")
    String sayHelloObjH(School school);

    /**
     * 嵌套对象测试:HTTP
     * @param student
     * @return
     */
    @POST
    @Path("httpTest/sayHelloNestedObj")
    String sayHelloNestedObjH(Student student);

    /**
     * 按照标注的入参对象和返回定义一个接口，不提供rest服务
     * @param requestBody
     * @return
     */
    public PlatResponseBody standMethodSofa(PlatRequestBody requestBody);

    /**
     * 按照标注的入参对象和返回定义一个接口，提供rest服务
     * @param requestBody
     * @return
     */
    @POST
    @Path("standMethodHttp")
    public PlatResponseBody standMethodHttp(PlatRequestBody requestBody);

    @Path("uploadFile")
    String uploadFile( List<File> files );

}
