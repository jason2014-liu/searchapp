/**
* TODO
* @Project: searchapp
* @Title: SearchProcessor.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 上午11:45:37
* @Copyright
* @Version 
*/
package com.lmstudio.search.batch;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmstudio.search.analyzer.ResultAnalyzer;
import com.lmstudio.search.analyzer.SearchEngine;
import com.lmstudio.search.analyzer.SearchService;
import com.lmstudio.search.model.SearchTaskEnt;
import com.lmstudio.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: SearchProcessor
 * @author jason
 */
public class SearchProcessor implements ItemProcessor<SearchTaskEnt, SearchTaskResult> {

	private static Logger log = LoggerFactory.getLogger(SearchProcessor.class);

	@Autowired
	private SearchSettings searchSettins;

	@Autowired
	private SearchService searchService;

	@Autowired
	private ResultAnalyzer resultAnalyzer;

	@Override
	public SearchTaskResult process(SearchTaskEnt item) throws Exception {
		// TODO Auto-generated method stub
		SearchTaskResult result = null;
		String url = searchSettins.getBaiduUrl() + URLEncoder.encode(item.getEntyName(), "UTF-8");

		if (searchSettins.getEngine().equals(SearchEngine.BAIDU.getEngineName())) {

			String html = searchService.search(url, SearchEngine.BAIDU);
			result = resultAnalyzer.analyzer(html, item);
		}

		return result;
	}

}
