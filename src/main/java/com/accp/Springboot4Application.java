package com.accp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.accp.ws.SysMessageSocketHanlder;

@SpringBootApplication
public class Springboot4Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot4Application.class, args);
//		SpringApplication springApplication = new SpringApplication(Springboot4Application.class);        
//		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);        
//		SysMessageSocketHanlder.ac=configurableApplicationContext;//【非常重要.........】
	}
}
