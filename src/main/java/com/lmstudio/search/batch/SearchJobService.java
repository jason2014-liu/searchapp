/**
* TODO
* @Project: searchapp
* @Title: SearchJobService.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 下午3:11:41
* @Copyright
* @Version 
*/
package com.lmstudio.search.batch;

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
import org.springframework.stereotype.Service;

/**
 * TODO
 * 
 * @ClassName: SearchJobService
 * @author jason
 */
@Service
public class SearchJobService {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job searchJob;
	
	public JobParameters jobParameters;

	public String search(String jobId) throws Exception {
		
		jobParameters = new JobParametersBuilder().addString("taskId", jobId)
				.addLong("time", System.currentTimeMillis()).toJobParameters();

		String msg = null;
		try {
			JobExecution jobExecute = jobLauncher.run(searchJob, jobParameters);
			ExitStatus status = jobExecute.getExitStatus();
			if (status.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
				System.out.println("任务正常完成");
				msg = "任务正常完成";
			} else {
				System.out.println("任务失败，exitCode=" + status.getExitCode());
				msg = "任务失败，exitCode=" + status.getExitCode();
			}

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}
}
