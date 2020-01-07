package com.wish.plat.base.api.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.wish.plat.base.api.OperService;
import org.springframework.stereotype.Service;

/**
 * @author: QUAN
 * @date: Created in 2020/1/6 20:05
 * @description:
 * @modified By:
 */
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "rest"),@SofaServiceBinding(bindingType = "bolt")})
public class OperServiceImpl2 implements OperService {
    @Override
    public String getOperListByOrg(String city_id, String org_code) {
        return "456";
    }
}
