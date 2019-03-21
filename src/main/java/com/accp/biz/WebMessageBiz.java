package com.accp.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accp.dao.IWebMessageDao;
import com.accp.pojo.WebMessage;

@Service("messageBiz")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, readOnly = true)
public class WebMessageBiz {

	@Autowired
	private IWebMessageDao messageDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
	public void addMessage(WebMessage message) {
		messageDao.saveMessage(message);
	}

	public List<WebMessage> findMessageList() {
		return messageDao.queryAllMessage();
	}

}
