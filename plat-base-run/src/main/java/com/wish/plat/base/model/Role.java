package com.wish.plat.base.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 柜员基本信息
 */
@Getter
@Setter
public class Role {
    private int id;
    private String city_id;
    private String role_name;
    private String recdac;
}
