package com.accp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.accp.biz.WebMessageBiz;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot4ApplicationTests {
	
	@Autowired
	private WebMessageBiz biz;
	
	@Test
	public void contextLoads() {
		biz.findMessageList();
	}

}
