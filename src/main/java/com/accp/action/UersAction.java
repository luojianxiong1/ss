package com.accp.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accp.pojo.Users;

@Controller
@RequestMapping("/c/users")
public class UersAction {

	@GetMapping("getUsers")
	@ResponseBody
	public Users getUsers(HttpSession session) {
		return (Users) session.getAttribute("USERS");
	}

	@PostMapping("loginIn")
	public String validateUsersInfo(Model model, Users users, HttpSession session) {
		if ("admin".equals(users.getUserName()) && "123".equals(users.getUserPwd())) {
			session.setAttribute("USERS", new Users("admin", "123", 0, new Date()));
			return "redirect:/msg.html";
		} else if ("sa".equals(users.getUserName()) && "123".equals(users.getUserPwd())) {
			session.setAttribute("USERS", new Users("sa", "123", 0, new Date()));
			return "redirect:/msg.html";
		} else {
			model.addAttribute("MSG", "用户名或密码错误");
			return "login";// 必须是模板文件名【转发】
		}
	}

}
