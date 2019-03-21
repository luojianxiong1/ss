package com.accp.ws;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/ws/talk/{name}") // 非常重要,只能绑定onopen
@Component
public class MyWebSockectHandler {

	private static final Set<Session> usersSet = new CopyOnWriteArraySet<Session>();// 保存用户状态

	private static final Map<String, Session> usersMap = new ConcurrentHashMap<String, Session>();// 保存用户状态

	private String name;

	@OnOpen
	public void onopen(Session session, @PathParam("name") String name) {
		System.out.println("握手成功，可以和客户端【" + session.getId() + "】交互");
		usersSet.add(session);
		usersMap.put(name, session);
		this.name = name;
		// 回送消息
		try {
			session.getBasicRemote().sendText("pong");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onclose(Session session) {
		System.out.println("客户端【" + session.getId() + "】关闭连接");
		usersSet.remove(session);
		usersMap.remove(name);
	}

	@OnMessage
	public void onmessage(String msg, Session session) {// 位置不可以改变
		System.out.println("接收客服端【" + session.getId() + "】信息");
		if (!"ping".equals(msg)) {
			if ("all".equals(msg)) {
				this.sendAllUsers("群主广播消息了:" + msg);
			} else if (msg.contains("@")) {
				String[] cs=msg.split("@");
				this.sendUser(cs[1],cs[0]);
			} else {
				try {
					session.getBasicRemote().sendText("服务器回复你的消息：美女好!");
				} catch (IOException e) {
					return;
				}
			}
		}
	}

	@OnError
	public void onerror(Session session, Throwable ex) {
		System.out.println("客服端【" + session.getId() + "】信息交互异常");
		usersSet.remove(session);
		usersMap.remove(name);
	}

	/**
	 * 
	 * @title: sendAllUsers
	 * @description: 群发
	 * @param msg
	 */
	private void sendAllUsers(String msg) {
		for (Session session : usersSet) {
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				continue;
			}
		}
	}

	/**
	 * 
	 * @title: sendUser
	 * @description: 单发
	 * @param name
	 * @param msg
	 */
	private void sendUser(String name, String msg) {
		try {
			usersMap.get(name).getBasicRemote().sendText(this.name+"说:"+msg);
		} catch (IOException e) {
			return;
		}
	}

}
