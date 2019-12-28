package com.wish.plat.base.model;

import lombok.Data;

/**
 * @author: QUAN
 * @date: Created in 2019/8/15 10:36
 * @description: 嵌套对象测试
 * @modified By:
 */
@Data
public class Student {
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;

    /**
     * 所属学校
     */
    private School school;
}
