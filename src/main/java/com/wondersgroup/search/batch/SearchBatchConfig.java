/**
* TODO
* @Project: searchapp
* @Title: SearchBatchConfig.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 上午11:29:11
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.wondersgroup.search.model.NmkSearchResult;
import com.wondersgroup.search.model.NmkTaskNetCheck;
import com.wondersgroup.search.model.SearchTaskEnt;
import com.wondersgroup.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: SearchBatchConfig
 * @author jason
 */
@Configuration
@EnableBatchProcessing
public class SearchBatchConfig {
	
	@Bean
	@StepScope
	public JdbcPagingItemReader<SearchTaskEnt> reader(@Value("#{jobParameters['taskId']}")  String taskId, DataSource dataSource) throws Exception{
		JdbcPagingItemReader<SearchTaskEnt> reader = new JdbcPagingItemReader<SearchTaskEnt>();
		
		reader.setDataSource(dataSource);
		
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDatabaseType(DatabaseType.ORACLE.getProductName());
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("SELECT PRI_ID,ENTY_ID,ENTY_TYPE,ENTY_NAME,TASK_ID");
		queryProvider.setFromClause("FROM "+SearchConstants.SCHEMA+"NMK_SEARCH_TASK_ENT");
		queryProvider.setWhereClause("WHERE TASK_ID = :task_id");
		Map<String,Order> sortKeys = new HashMap<String, Order>();
		sortKeys.put("PRI_ID", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider.getObject());
		
		reader.setPageSize(500);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("task_id", taskId);
		reader.setParameterValues(params);

		SearchTaskEntyRowMapper rowMapper = new SearchTaskEntyRowMapper();
		reader.setRowMapper(rowMapper);
		
		return reader;
	}

	@Bean
	public ItemProcessor<SearchTaskEnt, SearchTaskResult> processor() {
		 return new SearchProcessor();
	}
	
	@Bean
	public ItemWriter<SearchTaskResult> writer(DataSource dataSource){
		SearchWriter writer =  new SearchWriter();
		writer.setDataSource(dataSource);
		return writer;
	}
	
	
	
	
//	@Bean
//	public JdbcBatchItemWriter<List<SearchTaskResult>> writer(DataSource dataSource){
//		
//		JdbcBatchItemWriter<List<SearchTaskResult>> writer = new JdbcBatchItemWriter<List<SearchTaskResult>>();
//		writer.setDataSource(dataSource);
//		writer.setSql("insert into nmk2.nmk_search_task_result (ID,REL_ID,ENTY_NAME,WEBSITE,KEYWORD) values(:id,:relId,:entyName,:website,:keyword)");
//		BeanPropertyItemSqlParameterSourceProvider<List<SearchTaskResult>> sqlParamProvider = new BeanPropertyItemSqlParameterSourceProvider<List<SearchTaskResult>>();
//		writer.setItemSqlParameterSourceProvider(sqlParamProvider);
//		return writer;
//		
//	}
	
	/**
	* TODO 主体搜索任务
	* @Title: entJob
	* @param jobs
	* @param entStep
	* @return
	 */
	@Bean
	public Job entJob(JobBuilderFactory jobs, Step entStep) {
		return jobs.get("entJob")
				.incrementer(new RunIdIncrementer())
				.flow(entStep) //1
				.end()
				.listener(searchJobListener()) //2
				.build();
	}

	@Bean
	public Step entStep(StepBuilderFactory stepBuilderFactory, ItemReader<SearchTaskEnt> reader, ItemWriter<SearchTaskResult> writer,
			ItemProcessor<SearchTaskEnt,SearchTaskResult> processor) {
		return stepBuilderFactory
				.get("entStep")
				.<SearchTaskEnt, SearchTaskResult>chunk(10) //1
				.reader(reader) //2
				.processor(processor) //3
				.writer(writer) //4
				.build();
	}
	
	@Bean
	@StepScope
	public JdbcPagingItemReader<NmkTaskNetCheck> netReader(@Value("#{jobParameters['taskId']}")  String taskId, DataSource dataSource) throws Exception{
		JdbcPagingItemReader<NmkTaskNetCheck> reader = new JdbcPagingItemReader<NmkTaskNetCheck>();
		
		reader.setDataSource(dataSource);
		
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDatabaseType(DatabaseType.ORACLE.getProductName());
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("select net.LIST_ID,net.TASK_ID,net.NET_ID,net.DOMAIN,task.INCLUDE_ALL,task.INCLUDE_ANY,task.NOT_INCLUDE");
		queryProvider.setFromClause("from "+SearchConstants.SCHEMA+"nmk_check_task task ,"+SearchConstants.SCHEMA+"nmk_task_net_check net");
		queryProvider.setWhereClause("where task.LIST_ID = net.TASK_ID and task.LIST_ID = :task_id");
		Map<String,Order> sortKeys = new HashMap<String, Order>();
		sortKeys.put("LIST_ID", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider.getObject());
		
		reader.setPageSize(500);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("task_id", taskId);
		reader.setParameterValues(params);

		NmkTaskNetRowMapper rowMapper = new NmkTaskNetRowMapper();
		reader.setRowMapper(rowMapper);
		
		return reader;
	}
	
	@Bean
	public ItemProcessor<NmkTaskNetCheck, NmkSearchResult> netProcessor(){
		return new NmkTaskNetProcessor();
	}
	
	@Bean
	public ItemWriter<NmkSearchResult> netWriter(DataSource dataSource){
		NmkTaskNetWriter writer = new NmkTaskNetWriter();
		writer.setDataSource(dataSource);
		return writer;
	}
	
	/**
	* TODO 专项高级搜索任务
	* @Title: netJob
	* @param jobs
	* @param step1
	* @return
	 */
	@Bean
	public Job netJob(JobBuilderFactory jobs, Step netStep) {
		return jobs.get("netJob")
				.incrementer(new RunIdIncrementer())
				.flow(netStep) //1
				.end()
				.listener(searchJobListener()) //2
				.build();
	}

	@Bean
	public Step netStep(StepBuilderFactory stepBuilderFactory, ItemReader<NmkTaskNetCheck> reader, ItemWriter<NmkSearchResult> writer,
			ItemProcessor<NmkTaskNetCheck,NmkSearchResult> processor) {
		return stepBuilderFactory
				.get("netStep")
				.<NmkTaskNetCheck, NmkSearchResult>chunk(10) //1
				.reader(reader) //2
				.processor(processor) //3
				.writer(writer) //4
				.build();
	}
	

	
	/**
	* TODO 任务库
	* @Title: jobRepository
	* @param dataSource
	* @param transactionManager
	* @return
	* @throws Exception
	 */
	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType(DatabaseType.ORACLE.getProductName());
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		jobRepositoryFactoryBean.setMaxVarCharLength(3000);
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
		return jobLauncher;
	}
	
	@Bean
	public SearchJobListener searchJobListener() {
		return new SearchJobListener();
	}
}
