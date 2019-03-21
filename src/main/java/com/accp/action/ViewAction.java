package com.accp.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewAction {
	
	@GetMapping("login")
	public String goToLogin() {
		return "login";//模板文件名
	}

}
