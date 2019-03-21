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



@ServerEndpoint("/ww/talk/{name}")
@Component
public class handler {
	
	private static final Set<Session> usersSet=new CopyOnWriteArraySet<Session>();
	
	private static final Map<String,Session> usersMap=new ConcurrentHashMap<String,Session>();
	
	private String name;
	@OnOpen
	public void onopen(Session session,@PathParam("name") String name) {
		System.out.println(session.getId()+"链接成功"+name);
		usersSet.add(session);
		usersMap.put(name, session);
		this.name=name;
		this.queryname(this.name);
		try {
			session.getBasicRemote().sendText("pong");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@OnClose
	public void onclose(Session session) {
		System.err.println(session.getId()+"关闭连接");
		usersSet.remove(session);
		usersMap.remove(name);
	}
	@OnMessage
	public void onmessage(String msg, Session session) {
		System.err.println(session.getId()+"发送消息");
		
		if(!"ping".equals(msg)){
			if(msg.contains("#")) {
				this.sendAllUsers(this.name,msg);
			}else if(msg.contains("@")){
				String[] cs=msg.split("@");
				this.sendUser(cs[1],cs[0]);
			}else {
				try {
					session.getBasicRemote().sendText("服务器回复你的消息：美女好!");
				} catch (IOException e) {
					return;
				}
			}
//			if ("all".equals(msg)) {
//				this.sendAllUsers(this.name,msg);
//			} else if (msg.contains("@")) {
//				String[] cs=msg.split("@");
//				this.sendUser(cs[1],cs[0]);
//			} else {
//				try {
//					session.getBasicRemote().sendText("服务器回复你的消息：美女好!");
//				} catch (IOException e) {
//					return;
//				}
//			}
		}else {
			System.err.println("链接成功");
		}
	}
	@OnError
	public void onerror(Session session,Throwable ex) {
		System.out.println("客服端【" + session.getId() + "】信息交互异常");
	}
	
	public void sendAllUsers(String name,String msg) {
		for (Session session : usersSet) {
			try {
				session.getBasicRemote().sendText(this.name+"说:"+msg);
			} catch (IOException e) {
				continue;
			}
		}
	}
	public void sendUser(String name,String msg) {
		try {
			usersMap.get(name).getBasicRemote().sendText(this.name+"说:"+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void queryname(String name) {
			try {
				usersMap.get(name).getBasicRemote().sendText(usersMap.keySet().toString());
			} catch (IOException e) {
				
			}
	}
}
