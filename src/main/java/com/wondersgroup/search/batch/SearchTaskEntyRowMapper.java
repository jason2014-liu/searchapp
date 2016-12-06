/**
* TODO
* @Project: searchapp
* @Title: WebSearchEntyRowMapper.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 下午2:26:33
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wondersgroup.search.model.SearchTaskEnt;

/**
* TODO
* @ClassName: WebSearchEntyRowMapper
* @author jason
*/
public class SearchTaskEntyRowMapper implements RowMapper<SearchTaskEnt> {

	final static String COL_PRIID = "PRI_ID";
	final static String COL_ENTYID = "ENTY_ID";
	final static String COL_ENTYTYPE = "ENTY_TYPE";
	final static String COL_ENTYNAME = "ENTY_NAME";
	final static String COL_TASKID  = "TASK_ID";
	
	@Override
	public SearchTaskEnt mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		SearchTaskEnt searchTaskEnt = new SearchTaskEnt();
		searchTaskEnt.setPriId(rs.getString(COL_PRIID));
		searchTaskEnt.setEntyId(rs.getString(COL_ENTYID));
		searchTaskEnt.setEntyName(rs.getString(COL_ENTYNAME));
		searchTaskEnt.setEntyType(rs.getString(COL_ENTYTYPE));
		searchTaskEnt.setTaskId(rs.getString(COL_TASKID));
		
		return searchTaskEnt;
	}

}
