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
package com.lmstudio.search.batch;


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
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.lmstudio.search.model.SearchTaskEnt;
import com.lmstudio.search.model.SearchTaskResult;

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
		queryProvider.setSelectClause("select REL_ID ,ENTY_ID ,ENTY_TYPE ,ENTY_NAME ,TASK_ID");
		queryProvider.setFromClause("from nmk2.nmk_search_task_ent");
		queryProvider.setWhereClause("where task_id = :task_id");
		queryProvider.setSortKey("enty_id");
		reader.setQueryProvider(queryProvider.getObject());
		
		reader.setPageSize(1);
		
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
	
	@Bean
	public Job searchJob(JobBuilderFactory jobs, Step step1) {
		return jobs.get("searchJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1) //1
				.end()
				.listener(searchJobListener()) //2
				.build();
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<SearchTaskEnt> reader, ItemWriter<SearchTaskResult> writer,
			ItemProcessor<SearchTaskEnt,SearchTaskResult> processor) {
		return stepBuilderFactory
				.get("step1")
				.<SearchTaskEnt, SearchTaskResult>chunk(65000) //1
				.reader(reader) //2
				.processor(processor) //3
				.writer(writer) //4
				.build();
	}
	

	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType(DatabaseType.ORACLE.getProductName());
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
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
