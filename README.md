# A：其他工程调用base-api示例

## 1.配置sofa服务端地址 
```
wish.plat.base.url=172.29.12.100:12201
```  
## 2.pom引入接口包
```
<dependency>
    <groupId>com.wish</groupId>
    <artifactId>plat-base-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
``` 
## 3.启动类载入sofa客户端配置文件
```
@ImportResource({"classpath*:rpc-start-base-client.xml"})
```  
注：如果引入后bean无法注入，请检查此项或增加如下配置。
```  
@ComponentScan({"com.wish.plat.base.api.OperService","com.example.demo"})
```  
## 4.启动工程，发起请求
测试代码：
```
@Autowired
public OperService operService;

@RequestMapping("/getOperInfoByLoginID")
public OperResponceBody getOperInfoByLoginID(){
    return operService.getOperInfoByLoginID("1","2");
}
```
调用地址：
```
http://localhost:8090/getOperInfoByLoginID
```
返回结果：
```
{
	"data": {
		"common": null,
		"business": {
			"one": {
				"common": null,
				"business": null,
				"pageState": null,
				"oper_code": "1人员ID",
				"oper_name": "1人员名称",
				"login_id": "1登录名",
				"org_id": "1机构ID",
				"org_name": "1机构名称",
				"id_number": "1XX身份证",
				"email": "1@163.com",
				"mobile": "1110"
			}
		},
		"pageState": null,
		"oper_code": null,
		"oper_name": null,
		"login_id": null,
		"org_id": null,
		"org_name": null,
		"id_number": null,
		"email": null,
		"mobile": null
	}
}
```

# B：plat-base工程发布sofa接口步骤
## 1.在plat-base-api下定义接口
```
@Path("/base/api/oper")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface OperService {
    /**
     * 1、获取机构人员接口
     *
     * @param city_id  城市代码
     * @param org_code 机构代码
     * @return 柜员信息对象
     */
    @GET
    @Path("getOperListByOrg/{city_id}/{org_code}")
    OperResponceBody getOperListByOrg(@PathParam("city_id") String city_id, @PathParam("org_code") String org_code);
}
```  
## 2.在plat-base-run下实现该接口，并采用注解的方式发布服务。
```
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "rest"),@SofaServiceBinding(bindingType = "bolt")})
public class OperServiceImpl implements OperService {

    @Override
    public OperResponceBody getOperListByOrg(String city_id, String org_code) {
        System.out.print("入参city_id：" + city_id) ;
        System.out.print("入参org_code：" + org_code) ;
        return this.getOperResponceBody();
    }
}
```
注意：rest协议为前端直接使用，bolt协议用做后台rpc调用
## 3.在plat-base-api中引用上一步发布的服务
```
<!--柜员-->
<sofa:reference id="operServiceReference" interface="com.wish.plat.base.api.OperService">
    <sofa:binding.bolt>
        <sofa:global-attrs timeout="3000" address-wait-time="2000"/>
        <sofa:route target-url="${wish.plat.base.url:127.0.0.1:12200}"/>
    </sofa:binding.bolt>
</sofa:reference>
```
注意：文件是resources下rpc-start-base-client.xml文件
## 4.验证是否发布成功
访问：
```
http://172.29.12.100:12101/base/api/oper/getOperListByOrg/1/2
```
返回：
```
{
	"data": {
		"common": null,
		"business": {
			"list": [
				{
					"common": null,
					"business": null,
					"pageState": null,
					"oper_code": "1人员ID",
					"oper_name": "1人员名称",
					"login_id": "1登录名",
					"org_id": "1机构ID",
					"org_name": "1机构名称",
					"id_number": "1XX身份证",
					"email": "1@163.com",
					"mobile": "1110"
				},
				{
					"common": null,
					"business": null,
					"pageState": null,
					"oper_code": "2人员ID",
					"oper_name": "2人员名称",
					"login_id": "2登录名",
					"org_id": "2机构ID",
					"org_name": "2机构名称",
					"id_number": "2XX身份证",
					"email": "2@163.com",
					"mobile": "2110"
				},
				{
					"common": null,
					"business": null,
					"pageState": null,
					"oper_code": "3人员ID",
					"oper_name": "3人员名称",
					"login_id": "3登录名",
					"org_id": "3机构ID",
					"org_name": "3机构名称",
					"id_number": "3XX身份证",
					"email": "3@163.com",
					"mobile": "3110"
				}
			]
		},
		"pageState": null,
		"oper_code": null,
		"oper_name": null,
		"login_id": null,
		"org_id": null,
		"org_name": null,
		"id_number": null,
		"email": null,
		"mobile": null
	}
}
```
## 5.发布POST服务
```
@POST
@Path("add")
RoleData add(RoleData role);

请求地址：
http://127.0.0.1:12100/base/api/role/add
入参：
{
	"role_id":"id",
	"role_code":"code"
}
返回：
{
"common": null,
"business": null,
"pageState": null,
"role_id": "id",
"role_code": "code",
"role_name": null
}
```

# C：如何使用公共日志追加功能
## 1.使用须知
```
1.目前只针对sofa服务有效
```  
## 2.如何使用
## 2.1. 引入需要的jar
```
<!--引入日志追加公共信息jar-->
<dependency>
	<groupId>com.wish</groupId>
	<artifactId>plat-logtracer</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```
## 2.2. 新增sofa过滤器的扩展文件
```
路径：META-INF/services/sofa-rpc/com.alipay.sofa.rpc.filter.Filter
内容：globalSofaFilter=com.wish.logtracer.tracer.filter.GlobalSofaFilter
```
来源：https://www.sofastack.tech/projects/sofa-rpc/custom-filter/
## 2.3. 增加VM options参数
```
目的：为日志增加事件工厂
-DLog4jLogEventFactory=com.wish.logtracer.tracer.event.CommonMsgEventFactory
```
## 2.4. 调整log4j2配置
```
"[common:%commonMsg] %d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c{1}:%L -%m%n"
```
来源：
https://www.jianshu.com/p/d7110c599e9c
https://www.jianshu.com/p/b9c18c2987dc
## 3.达到的效果
## 3.1. 启动项目时，日志示例
```
[common:] 2019-08-30 17:15:27,825 INFO  RunApplication:59 -Started RunApplication in 28.515 seconds (JVM running for 34.266)
```
## 3.2. 在发布的sofa服务中，增加写日志的代码
```
log.info("Server receive1: " + name);
log.info("Server receive2: " + name);
log.info("Server receive3: " + name);
```
## 3.3. 通过plat-gateway转到sofa服务，进行调用。日志示例
```
start thread Id:59
end thread Id:59
[common:{"threadId":"17","businessDate":"2019-07-27","loginId":"123","channel":"WeiXin","cityId":"quanxin","systemTime":"2019-07-27:08:56:00"}] 2019-08-30 17:15:37,843 INFO  HelloServiceImpl:23 -Server receive1: quanxin
[common:{"threadId":"17","businessDate":"2019-07-27","loginId":"123","channel":"WeiXin","cityId":"quanxin","systemTime":"2019-07-27:08:56:00"}] 2019-08-30 17:15:37,843 INFO  HelloServiceImpl:24 -Server receive2: quanxin
[common:{"threadId":"17","businessDate":"2019-07-27","loginId":"123","channel":"WeiXin","cityId":"quanxin","systemTime":"2019-07-27:08:56:00"}] 2019-08-30 17:15:37,843 INFO  HelloServiceImpl:25 -Server receive3: quanxin
```
## 3.4. 在kibana上，日志示例
```
August 30th 2019, 17:14:18.729	
type:log-base @version:1 @timestamp:August 30th 2019, 17:14:18.729 message:[common:{"threadId":"17","businessDate":"2019-07-27","loginId":"123","channel":"WeiXin","cityId":"quanxin","systemTime":"2019-07-27:08:56:00"}] 
2019-08-30 17:15:37,843 INFO HelloServiceImpl:25 -Server receive3: quanxin _id:V-vN4WwB8O1YgcWL1ZlV _type:doc _index:log-base-20190830 _score:
```
# D：如何使用SOFATracer及上报Zipkin
## 1.使用须知
```
1.目前是傻瓜式应用，仅仅是单节点打印出TracerId
2.真正的使用应该结合网关，多个服务，分别在不同机器上，打印出TraceId
3.本次验证的只是：应用日志打印 traceId 和 spanId
4.纯属个人立场，仅供交流
```  
## 2.如何使用
### 2.1引入jar
```
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
<!--上报数据至 Zipkin-->
<dependency>
    <groupId>io.zipkin.zipkin2</groupId>
    <artifactId>zipkin</artifactId>
    <version>2.11.12</version>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter</artifactId>
    <version>2.7.13</version>
</dependency>
```
### 2.2集成log4j2
```
value: "[%X{SOFA-TraceId},%X{SOFA-SpanId}] %d{yyyy-MM-dd HH:mm:ss,SSS} %-5p %c{1}:%L -%m%n"
```
参考：https://www.sofastack.tech/projects/sofa-tracer/print-traceid-spanid/
### 2.3配置zipkin
```
  tracer:
    zipkin:
      enabled: true
      baseUrl: http://yf009.intdev.hxyd.tech:9411
```
## 3.达到了什么效果
```
[ac1d1997157835721228410027876,0] 2020-01-07 08:33:32,388 INFO  HelloServiceImpl:26 -Server receive1: 123
```
参考：https://www.sofastack.tech/projects/sofa-tracer/report-to-zipkin/

# E：两个实现类实现同一个接口（韩）
## 1.使用须知
```
1.与韩老师交流时，对于此情况好奇，因此作出如下验证
2.因为客户端引入的是接口类，并无实现类，因此客户端无法根据bean名称实例不同的实现类
3.本次的结论：任意执行了其中一个实现类
4.纯属个人立场，仅供交流
```  
## 2.如何进行验证
### 2.1在接口工程定义接口
```
略
```
### 2.2在服务端实现接口
```
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "rest"),@SofaServiceBinding(bindingType = "bolt")})
public class OperServiceImpl implements OperService {
    @Override
    public String getOperListByOrg(String city_id, String org_code) {
        return "123";
    }
}
@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "rest"),@SofaServiceBinding(bindingType = "bolt")})
public class OperServiceImpl2 implements OperService {
    @Override
    public String getOperListByOrg(String city_id, String org_code) {
        return "456";
    }
}
```
### 2.3客户端使用
```
@RestController
public class HelloController {

    @Autowired
    OperService OperService;

    @RequestMapping("/1")
    public String OperServiceImpl(){
        return  OperService.getOperListByOrg("1","2");
    }
}
```
### 2.4验证结果
```
访问：http://localhost:8080/1
返回：456
```
注：本工程为服务端，测试的客户端见：https://github.com/quan2631/plat-base-api-client
