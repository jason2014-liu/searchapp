/**
* TODO
* @Project: searchapp
* @Title: NmkTaskNetProcessor.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年12月2日 下午1:34:26
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondersgroup.search.analyzer.ResultAnalyzer;
import com.wondersgroup.search.analyzer.SearchEngine;
import com.wondersgroup.search.analyzer.SearchService;
import com.wondersgroup.search.model.NmkSearchResult;
import com.wondersgroup.search.model.NmkTaskNetCheck;

/**
 * TODO
 * 
 * @ClassName: NmkTaskNetProcessor
 * @author jason
 */
public class NmkTaskNetProcessor implements ItemProcessor<NmkTaskNetCheck, NmkSearchResult> {

	private static Logger log = LoggerFactory.getLogger(NmkTaskNetProcessor.class);

	@Autowired
	private SearchSettings searchSettins;

	@Autowired
	private SearchService searchService;

	@Autowired
	private ResultAnalyzer resultAnalyzer;

	@Override
	public NmkSearchResult process(NmkTaskNetCheck item) throws Exception {
		// TODO Auto-generated method stub

		String keyword = formatKeyWord(item);

		NmkSearchResult result = null;
		String url = searchSettins.getBaiduUrl() + URLEncoder.encode(keyword, "UTF-8");

		if (searchSettins.getEngine().equals(SearchEngine.BAIDU.getEngineName())) {

			String html = searchService.search(url, SearchEngine.BAIDU);
			result = resultAnalyzer.analyzer(html, item);
		}

		return result;

	}

	/**
	* TODO
	* @Title: formatKeyWord
	* @param item
	* @return
	* @throws IOException
	 */
	public String formatKeyWord(NmkTaskNetCheck item) throws IOException {
		StringBuilder builder = new StringBuilder();

		builder.append("site:(").append(StringUtils.trimWhitespace(item.getDomain()).replaceAll("http://", "").replaceAll("https://", "")).append(")");

		String includeAll = item.getIncludeAll();
		String includeAny = item.getIncludeAny();
		String notInclude = item.getNotInclude();

		ObjectMapper objectMapper = new ObjectMapper();

		List<String> list = null;
		StringBuilder sb = null;
		String tempStr = null;
		String[] strArray = null;

		if (!StringUtils.isEmpty(includeAll)) {
//			list = objectMapper.readValue(includeAll, List.class);
//			for (String str : list) {
//				builder.append(" ").append(str);
//			}
			strArray = includeAll.split(",");
			for(String str : strArray){
				builder.append(" ").append(str);
			}
			

		}

		if (!StringUtils.isEmpty(includeAny)) {
			//list = objectMapper.readValue(includeAny, List.class);
			strArray = includeAny.split(",");
			builder.append(" (");
			sb = new StringBuilder();
			for(String str : strArray){
				sb.append(str).append(" | ");
			}
//			for (String str : list) {
//				sb.append(str).append(" | ");
//			}

			tempStr = sb.toString();
			builder.append(tempStr.substring(0, tempStr.lastIndexOf(" | "))).append(")");

		}

		if (!StringUtils.isEmpty(notInclude)) {
			//list = objectMapper.readValue(notInclude, List.class);
			strArray = notInclude.split(",");
			builder.append(" -(");
			sb = new StringBuilder();
			for(String str : strArray){
				sb.append(str).append(" | ");
			}
//			for (String str : list) {
//				sb.append(str).append(" | ");
//			}

			tempStr = sb.toString();
			builder.append(tempStr.substring(0, tempStr.lastIndexOf(" | "))).append(")");

		}

		return builder.toString();
	}

}
