/**
* TODO
* @Project: searchapp
* @Title: TaskScheduler.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年12月5日 下午3:29:48
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 
 * @ClassName: TaskScheduler
 * @author jason
 */
@Component
@Configurable
@EnableScheduling
public class TaskScheduler {

	private static Logger log = LoggerFactory.getLogger(TaskScheduler.class);

	final String netSql = "select * from  "+SearchConstants.SCHEMA+"nmk_search_queue where TASK_TYPE = " + SearchConstants.TASK_TYPE_NET
			+ " and TASK_STATUS = " + SearchConstants.TASK_STAUS_NEW + " order by CREATE_DATE asc, ORDER_NUM asc";

	final String entSql = "select * from  "+SearchConstants.SCHEMA+"nmk_search_queue where TASK_TYPE = " + SearchConstants.TASK_TYPE_ENT
			+ " and TASK_STATUS = " + SearchConstants.TASK_STAUS_NEW + " order by CREATE_DATE asc, ORDER_NUM asc";

	final String updateSql = "update  "+SearchConstants.SCHEMA+"NMK_SEARCH_QUEUE set TASK_STATUS = "+SearchConstants.TASK_STATUS_COMPLETED+" ,complete_date = sysdate where task_id = :taskId";
	private DataSource dataSource;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("entJob")
	private Job entJob;

	@Autowired
	@Qualifier("netJob")
	private Job netJob;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * TODO 定时搜索任务 每天23点执行
	 * 
	 * @Title: excuSearchTask
	 */
	@Scheduled(cron = "0 0 23 * * ? ")
	public void scheduleTask() {
		// 专项任务

		namedParameterJdbcTemplate.query(netSql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				String taskId = rs.getString("TASK_ID");
				excuteTask(taskId, SearchConstants.TASK_TYPE_NET);
			}
		});

		// 主体搜索任务
		namedParameterJdbcTemplate.query(entSql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				String taskId = rs.getString("TASK_ID");
				excuteTask(taskId, SearchConstants.TASK_TYPE_ENT);
			}
		});
	}

	/**
	 * TODO
	 * 
	 * @Title: excuteTask
	 * @param taskId
	 * @param taskType
	 */
	public void excuteTask(String taskId, String taskType) {

		//.addLong("time", System.currentTimeMillis()). for test
		JobParameters jobParameters = new JobParametersBuilder().addString("taskId", taskId).addLong("time", System.currentTimeMillis()).toJobParameters();
		JobExecution jobExecute = null;
		try {
			if(SearchConstants.TASK_TYPE_ENT.equals(taskType)){
				jobExecute = jobLauncher.run(entJob, jobParameters);
			}else{
				jobExecute = jobLauncher.run(netJob, jobParameters);
			}
			
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("搜索任务 taskId=" + taskId + "执行失败。异常信息："+e.getMessage());
		}
		ExitStatus status = jobExecute.getExitStatus();
		Map<String,Object> params = null;
		if (ExitStatus.COMPLETED.getExitCode().equals(status.getExitCode())) {
			log.info("搜索任务 taskId=" + taskId + "执行成功。"+status.getExitCode()+status.getExitDescription());
			params = new HashMap<String,Object>();
			params.put("taskId", taskId);
			namedParameterJdbcTemplate.update(updateSql, params);
		} else {
			log.info("搜索任务 taskId=" + taskId + "执行失败"+status.getExitCode()+status.getExitDescription());
		}

	}

}
