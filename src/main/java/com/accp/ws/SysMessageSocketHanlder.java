package com.accp.ws;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.accp.biz.TblLogBiz;
import com.accp.biz.WebMessageBiz;
import com.accp.pojo.TblLog;
import com.accp.pojo.Users;
import com.accp.ws.cfg.HttpSessionConfigurator;

@ServerEndpoint(value = "/ws/sys", configurator = HttpSessionConfigurator.class)
@Component
public class SysMessageSocketHanlder {

	private final static Map<String, Session> usersMap = new ConcurrentHashMap<String, Session>();

	public static ApplicationContext ac;// 非常重要

	private String userName;

	@OnOpen
	public void onopen(Session session, EndpointConfig config) {
		WebMessageBiz messageBiz = (WebMessageBiz) ac.getBean("messageBiz");
		TblLogBiz logBiz = (TblLogBiz) ac.getBean("logBiz");
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		final String _userName = ((Users) httpSession.getAttribute("USERS")).getUserName();
		this.userName = _userName;
		usersMap.put(this.userName, session);// 存入会话信息
		// 最新消息推送功能
		new Thread() {
			public void run() {
				int countInit = messageBiz.findMessageList().size();
				while (true) {
					try {
						session.getBasicRemote().sendText("pong");
						Thread.sleep(5000);
						int count = messageBiz.findMessageList().size();
						if (count > countInit) {
							session.getBasicRemote().sendText("reload");
							countInit = count;
						}
					} catch (Exception e) {
						logBiz.addTblLog(new TblLog(_userName + "用户推送消息失败"));
						break;
					}
				}
			}
		}.start();
	}

	/**
	 * 
	 * @title: sendAllUsers
	 * @description: Map模式群发
	 * @param msg
	 */
	private void sendAllUsers(String msg) throws Exception {
		for (String key : usersMap.keySet()) {
			try {
				usersMap.get(key).getBasicRemote().sendText(msg);
			} catch (IOException e) {
				continue;
			}
		}
	}

	@OnClose
	public void onclose(Session session) {
		usersMap.remove(this.userName);
	}

	@OnError
	public void onerror(Session session, Throwable ex) {
		usersMap.remove(this.userName);
	}

	@OnMessage
	public void onmessage(String msg, Session session) {

	}
}
