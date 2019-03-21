package com.accp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.accp.pojo.WebMessage;

public interface IWebMessageDao {

	public int saveMessage(@Param("message")WebMessage message);

	public List<WebMessage> queryAllMessage();
}
