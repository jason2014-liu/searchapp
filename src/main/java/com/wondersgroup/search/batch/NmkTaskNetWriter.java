/**
* TODO
* @Project: searchapp
* @Title: NmkTaskNetWriter.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年12月2日 下午4:50:28
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.wondersgroup.search.model.NmkSearchNetResult;
import com.wondersgroup.search.model.NmkSearchResult;

/**
 * TODO
 * 
 * @ClassName: NmkTaskNetWriter
 * @author jason
 */
public class NmkTaskNetWriter implements ItemWriter<NmkSearchResult> {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	static final String opeSQL = "insert into  "+SearchConstants.SCHEMA+"nmk_search_net_result(id,task_id,net_id,domain,result_title,result_url,result_summary,result_keyword,list_id) values(:id,:taskId,:netId,:domain,:resultTitle,:resultUrl,:resultSummary,:resultKeyword,:listId)";

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public int[] batchUpdate(List<NmkSearchNetResult> entResults) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entResults.toArray());
		int[] insertCounts = namedParameterJdbcTemplate.batchUpdate(opeSQL, batch);
		return insertCounts;
	}

	@Override
	public void write(List<? extends NmkSearchResult> items) throws Exception {
		// TODO Auto-generated method stub
		for (NmkSearchResult result : items) {
			if (result.getList() != null) {
				batchUpdate(result.getList());
			}
		}
	}

}
