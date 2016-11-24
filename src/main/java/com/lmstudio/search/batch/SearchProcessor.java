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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.lmstudio.search.analyzer.SearchEngine;
import com.lmstudio.search.model.SearchTaskEnt;
import com.lmstudio.search.model.SearchTaskEntResult;
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

	@Override
	public SearchTaskResult process(SearchTaskEnt item) throws Exception {
		// TODO Auto-generated method stub
		// log.debug("item:"+item.getEntyId());
		StringBuilder sb = new StringBuilder();
		SearchTaskResult result = new SearchTaskResult();

		if (searchSettins.getEngine().equals(SearchEngine.BAIDU.getEngineName())) {
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(searchSettins.getBaiduUrl() + URLEncoder.encode(item.getEntyName(), "UTF-8"));
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				HttpEntity entity1 = response1.getEntity();
				InputStream is = entity1.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String tmp = br.readLine();
				while(tmp!=null){
					sb.append(tmp).append("\n");
					tmp = br.readLine();
				}
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		}

		//log.debug("downHtml:\n"+sb.toString());
		
		Document doc = Jsoup.parse(sb.toString());
		Elements elements = doc.select("div#content_left");
		if(elements != null){
			elements = elements.first().children();
		}
		SearchTaskEntResult entResult = null;
		for (Element element : elements) {
			
			String title = null;
			String url = null;
			String summary = null;
			Element e = null;
			if ((element.hasClass("result") || element.hasClass("result-op")) && element.hasClass("c-container")) {
				e = element.select("h3.t > a").first();
				
				//获取url
				url = e.attr("href");
				try {
					URL url2 = new URL(url);
					HttpURLConnection httpConn = (HttpURLConnection) url2.openConnection();
					httpConn.setConnectTimeout(3000);
					httpConn.connect();
					int responseCode = httpConn.getResponseCode();
					if (responseCode == 200 || responseCode == 403) {
						url = httpConn.getURL().toString();
						url = url.replaceAll("http://", "");
						url = SearchUtils.parseDomainOfUrl(url);
					}
				} catch (MalformedURLException ex) {
					// TODO Auto-generated catch block
					//ex.printStackTrace();
					url = null;
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					//ex.printStackTrace();
					url = null;
				}
				
				if(url != null && !url.contains("baidu.com")){
					// 获取标题
					title = e.html().replaceAll("<em>", "").replaceAll("</em>", "");
				
					entResult = new SearchTaskEntResult();
					entResult.setId(UUID.randomUUID().toString().replace("-", ""));
					entResult.setEntyName(item.getEntyName());
					entResult.setRelId(item.getRelId());
					entResult.setKeyword(title);
					entResult.setWebsite(url);

					result.addEntResult(entResult);
				}
				
			}

			log.debug("title:" + title + "\n url:" + url + "\n summary:" + summary + "\n");
		}

		return result;
	}

}
