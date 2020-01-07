package com.wish.plat.base.api;

import javax.ws.rs.*;

/**
 * @author: QUAN
 * @date: Created in 2020/1/6 20:00
 * @description:
 * @modified By:
 */
@Path("/base/api/oper")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface OperService {

    @GET
    @Path("getOperListByOrg/{city_id}/{org_code}")
    String getOperListByOrg(@PathParam("city_id") String city_id, @PathParam("org_code") String org_code);
}
