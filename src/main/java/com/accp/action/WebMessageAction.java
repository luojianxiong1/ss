package com.accp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accp.biz.WebMessageBiz;
import com.accp.pojo.WebMessage;

@RestController
@RequestMapping("/")
public class WebMessageAction {

	@Autowired
	private WebMessageBiz messageBiz;

	@RequestMapping("list")
	public List<WebMessage> getMessageList() throws Exception {
		return messageBiz.findMessageList();
	}

	@RequestMapping("add")
	public Map<String, String> addMessage(@RequestBody WebMessage message) throws Exception {
		Map<String, String> rsMessage = new HashMap<String, String>();
		messageBiz.addMessage(message);
		rsMessage.put("code", "200");
		rsMessage.put("msg", "ok");
		return rsMessage;

	}
}
