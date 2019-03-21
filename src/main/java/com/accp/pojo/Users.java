package com.accp.pojo;

import java.util.Date;

public class Users {
	
	private String userName;
	
	private String userPwd;
	
	private Integer userState;
	
	private Date userDate;

	public Users(String userName, String userPwd, Integer userState, Date userDate) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
		this.userState = userState;
		this.userDate = userDate;
	}

	public Users() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Date getUserDate() {
		return userDate;
	}

	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}
	
	
	
}
