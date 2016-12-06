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

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * TODO
 * 
 * @ClassName: SearchJobListener
 * @author jason
 */
public class SearchJobListener implements JobExecutionListener {

	long startTime;
	long endTime;

	@Override
	public void afterJob(JobExecution arg0) {
		// TODO Auto-generated method stub

		endTime = System.currentTimeMillis();
		System.out.println("任务处理结束");
		System.out.println("耗时:" + (endTime - startTime) + "ms");
	}

	@Override
	public void beforeJob(JobExecution arg0) {
		// TODO Auto-generated method stub

		startTime = System.currentTimeMillis();
		System.out.println("任务处理开始");
	}

}
