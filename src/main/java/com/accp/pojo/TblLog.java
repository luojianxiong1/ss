package com.accp.pojo;

import java.util.Date;

public class TblLog {

	private Integer logId;
	private String logDes;
	private Date logDate;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getLogDes() {
		return logDes;
	}

	public void setLogDes(String logDes) {
		this.logDes = logDes;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public TblLog(String logDes) {
		super();
		this.logDes = logDes;
	}

	public TblLog() {
		super();
	}

}
