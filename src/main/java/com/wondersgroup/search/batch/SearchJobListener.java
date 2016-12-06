/**
* TODO
* @Project: searchapp
* @Title: SearchJobListener.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 下午2:53:58
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;

/**
 * TODO
 * 
 * @ClassName: SearchJobListener
 * @author jason
 */
public class SearchJobListener implements JobExecutionListener {

	private static Logger log = LoggerFactory.getLogger(SearchJobListener.class);

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

		JobParameters jobParameters = jobExecution.getJobParameters();
		String taskId = jobParameters.getString("taskId");
		long costTime = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

		
		
		if (BatchStatus.COMPLETED == jobExecution.getStatus() && ExitStatus.COMPLETED == jobExecution.getExitStatus()) {
			log.info("任务ID=" + taskId + "执行完毕，花费时间：" + costTime + "ms");
		}
		
		if(BatchStatus.FAILED == jobExecution.getStatus()){
			log.info("任务ID=" + taskId + "执行失败，花费时间：" + costTime + "ms，异常信息："+formatExceptions(jobExecution.getAllFailureExceptions()));
		}

	}

	@Override
	public void beforeJob(JobExecution jobExcution) {
		// TODO Auto-generated method stub

	}
	
	/**
	* TODO
	* @Title: formatExceptions
	* @param exceptions
	* @return
	 */
	public String formatExceptions(List<Throwable> exceptions){
		
		StringBuilder builder = new StringBuilder();
		for(Throwable throwable : exceptions){
			builder.append(throwable.getMessage()).append("\n");
			
		}
		return builder.toString();
	}

}
