package com.accp.dao;


import org.apache.ibatis.annotations.Param;

import com.accp.pojo.TblLog;

public interface ITblLogDao {

	public int saveTblLog(@Param("log")TblLog log);

}
