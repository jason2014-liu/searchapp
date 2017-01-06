/**
* TODO
* @Project: searchapp
* @Title: TestController.java
* @Package com.lmstudio.search.controller
* @author jason
* @Date 2016年11月21日 下午4:48:06
* @Copyright
* @Version 
*/
package com.wondersgroup.search.controller;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wondersgroup.search.batch.TaskScheduler;

/**
 * TODO http://localhost:9999/searchapp/test/search
 * 
 * @ClassName: TestController
 * @author jason
 */
@RequestMapping("/test")
@Controller
@EnableAutoConfiguration
public class TestController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("entJob")
	Job entJob;
	
	@Autowired
	@Qualifier("netJob")
	Job netJob;
	
	@Autowired
	TaskScheduler scheduler;
	
	/**
	 * 必须申明成public 的，才能被访问到
	 */
	public JobParameters jobParameters;

	@RequestMapping(value = "/search/{jobId}", method = RequestMethod.GET)
	@ResponseBody
	public String search(@PathVariable(name="jobId") String jobId) {

		jobParameters = new JobParametersBuilder().addString("taskId", jobId)
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		String msg = null;
		try {
			JobExecution jobExecute = jobLauncher.run(netJob, jobParameters);
			/**
			 * A job instance already exists and is complete for
			 * parameters={taskId=100}. If you want to run this job again,
			 * change the parameters  加个时间戳即可，满足测试期间，每条任务都被识别为不一样的
			 */
			ExitStatus status = jobExecute.getExitStatus();
			if (status.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
				System.out.println("任务正常完成");
				msg = "任务正常完成";
			} else {
				System.out.println("任务失败，exitCode=" + status.getExitCode());
				msg = "任务失败，exitCode=" + status.getExitCode();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}
	
	@RequestMapping(value = "/search/all", method = RequestMethod.GET)
	@ResponseBody
	public void test(){
		scheduler.scheduleTask();
	}
}
