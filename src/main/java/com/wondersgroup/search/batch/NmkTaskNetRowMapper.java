/**
* TODO
* @Project: searchapp
* @Title: NmkTaskNetRowMapper.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年12月2日 上午11:30:34
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wondersgroup.search.model.NmkTaskNetCheck;

/**
* TODO
* @ClassName: NmkTaskNetRowMapper
* @author jason
*/
public class NmkTaskNetRowMapper implements RowMapper<NmkTaskNetCheck> {
	
	final static String COL_LISTID = "LIST_ID";
	final static String COL_TASKID = "TASK_ID";
	final static String COL_NETID = "NET_ID";
	final static String COL_DOMAIN = "DOMAIN";
	final static String COL_INCLUDEALL = "INCLUDE_ALL";
	final static String COL_INCLUDEANY = "INCLUDE_ANY";
	final static String COL_NOTINCLUDE = "NOT_INCLUDE";
	

	@Override
	public NmkTaskNetCheck mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		NmkTaskNetCheck net = new NmkTaskNetCheck();
		net.setListId(rs.getString(COL_LISTID));
		net.setTaskId(rs.getString(COL_TASKID));
		net.setNetId(rs.getString(COL_NETID));
		net.setDomain(rs.getString(COL_DOMAIN));
		
		net.setIncludeAll(rs.getString(COL_INCLUDEALL));
		net.setIncludeAny(rs.getString(COL_INCLUDEANY));
		net.setNotInclude(rs.getString(COL_NOTINCLUDE));
		return net;
	}

}
