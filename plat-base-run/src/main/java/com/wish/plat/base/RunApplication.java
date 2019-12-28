package com.wish.plat.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ImportResource({"classpath*:rpc-start-idgeneration-client.xml"})
@RestController
public class RunApplication {
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}

	@RequestMapping("/plat-base")
	public String base(){
	    return "hello, plat-base project.";
    }
}
