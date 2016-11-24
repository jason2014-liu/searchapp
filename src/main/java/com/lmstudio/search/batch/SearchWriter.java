/**
* TODO
* @Project: searchapp
* @Title: SearchWriter.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月22日 下午5:22:48
* @Copyright
* @Version 
*/
package com.lmstudio.search.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.lmstudio.search.model.SearchTaskEntResult;
import com.lmstudio.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: SearchWriter
 * @author jason
 */
public class SearchWriter implements ItemWriter<SearchTaskResult> {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	static final String opeSQL = "insert into nmk2.nmk_search_task_result (ID,REL_ID,ENTY_NAME,WEBSITE,KEYWORD) values(:id,:relId,:entyName,:website,:keyword)";

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public int[] batchUpdate(List<SearchTaskEntResult> entResults) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(entResults.toArray());
		int[] insertCounts = namedParameterJdbcTemplate
				.batchUpdate(opeSQL, batch);
		return insertCounts;
	}

	@Override
	public void write(List<? extends SearchTaskResult> items) throws Exception {
		// TODO Auto-generated method stub
		for (SearchTaskResult result : items) {
			if (result.getList() != null) {
				batchUpdate(result.getList());
			}

		}
	}

}
