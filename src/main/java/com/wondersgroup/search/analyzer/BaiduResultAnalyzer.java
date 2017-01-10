/**
* TODO
* @Project: searchapp
* @Title: BaiduResultAnalyzer.java
* @Package com.lmstudio.search.analyzer
* @author jason
* @Date 2016年11月15日 下午4:18:27
* @Copyright
* @Version 
*/
package com.wondersgroup.search.analyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wondersgroup.search.batch.SearchUtils;
import com.wondersgroup.search.model.NmkSearchNetResult;
import com.wondersgroup.search.model.NmkSearchResult;
import com.wondersgroup.search.model.NmkTaskNetCheck;
import com.wondersgroup.search.model.SearchTaskEnt;
import com.wondersgroup.search.model.SearchTaskEntResult;
import com.wondersgroup.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: BaiduResultAnalyzer
 * @author jason
 */
@Component(value = "baiduResultAnalyzer")
public class BaiduResultAnalyzer implements ResultAnalyzer {

	private static Logger log = LoggerFactory.getLogger(BaiduResultAnalyzer.class);

	@Override
	public SearchTaskResult analyzer(String html, SearchTaskEnt item) {
		// TODO Auto-generated method stub
		SearchTaskResult result = new SearchTaskResult();

		Document doc = Jsoup.parse(html);
		log.info(html);
		Elements elements = doc.select("div#content_left");
		if (elements != null && elements.first()!=null) {
			elements = elements.first().children();
		}else{
			log.info("搜索主体"+item.getEntyName()+"结果为空");
			return null;
		}

		SearchTaskEntResult entResult = null;
		for (Element element : elements) {

			String title = null;
			String url = null;
			String summary = null;
			Element e = null;

			if ((element.hasClass("result") || element.hasClass("result-op")) && element.hasClass("c-container")) {

				e = element.select("h3.t > a").first();

				// 获取url
				url = e.attr("href");
				try {
					URL url2 = new URL(url);
					HttpURLConnection httpConn = (HttpURLConnection) url2.openConnection();
					httpConn.setConnectTimeout(30000);
					httpConn.connect();
					int responseCode = httpConn.getResponseCode();
					if (responseCode == 200 || responseCode == 403) {
						url = httpConn.getURL().toString();
						url = url.replaceAll("http://", "");
						url = SearchUtils.parseDomainOfUrl(url);
					}
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					// ex.printStackTrace();
					log.error("主体搜索任务>>地址"+url+"访问异常："+ex.getMessage());
					url = null;
				}

				if (url != null && !url.contains("baidu.com")) {
					// 获取标题
					title = e.html().replaceAll("<em>", "").replaceAll("</em>", "");

					entResult = new SearchTaskEntResult();
					entResult.setId(SearchUtils.getPrimaryIdByUUID());
					entResult.setEntyName(item.getEntyName());
					entResult.setRelId(item.getPriId());
					entResult.setKeyword(title);
					entResult.setWebsite(url);

					result.addEntResult(entResult);

					log.info("主体("+item.getEntyName()+")搜索结果：title:" + title + " url:" + url + " summary:" + summary + "\n");
				}

			}

		}

		return result;
	}

	/**
	 * TODO
	 * 
	 * @Title: advancedSearch
	 */
	public void advancedSearch() {

		String testurl = null;

		try {
			testurl = "http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&wd="
					+ URLEncoder.encode("site:(www.wondersgroup.com) (万达 | 云计算)", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = null;
		try {
			doc = Jsoup.connect(testurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Elements elements = doc.select("div#content_left");
		if (elements != null) {
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

				// 获取url
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
					}
				}  catch (IOException ex) {
					// TODO Auto-generated catch block
					// ex.printStackTrace();
					log.error("专项搜索任务>>地址"+url+"访问异常："+ex.getMessage());
					url = null;
				}

				if (url != null && !url.contains("baidu.com")) {
					// 获取标题
					title = e.html().replaceAll("<em>", "").replaceAll("</em>", "");

					Element summaryEle = element.select("div.c-abstract").first();

					if (summaryEle == null) {
						summaryEle = element.select("div > div > div.c-abstract").first();
					}

					summary = summaryEle.html().replaceAll("<em>", "").replaceAll("</em>", "").replaceAll("\n", "");

					System.out.println("title:" + title + "\n url:" + url + "\n summary:" + summary + "\n");
				}

			}
		}
	}

	@Override
	public NmkSearchResult analyzer(String html, NmkTaskNetCheck item) {
		// TODO Auto-generated method stub

		NmkSearchResult result = new NmkSearchResult();

		Document doc = Jsoup.parse(html);
//		Elements nonelements = doc.select("div#content_none");
//		if(nonelements != null){
//			log.info("搜索网站"+item.getDomain()+"结果为空");
//			return null;
//		}
		
		Elements elements = doc.select("div#content_left");
		if (elements != null) {
			elements = elements.first().children();
		}else{
			log.info("搜索网站"+item.getDomain()+"结果为空");
			return null;
		}
		NmkSearchNetResult netResult = null;
		for (Element element : elements) {

			String title = null;
			String url = null;
			String summary = null;
			Element e = null;
			if ((element.hasClass("result") || element.hasClass("result-op")) && element.hasClass("c-container")) {
				e = element.select("h3.t > a").first();

				// 获取url
				url = e.attr("href");
				try {
					URL url2 = new URL(url);
					HttpURLConnection httpConn = (HttpURLConnection) url2.openConnection();
					httpConn.setConnectTimeout(30000);
					httpConn.connect();
					int responseCode = httpConn.getResponseCode();
					if (responseCode == 200 || responseCode == 403) {
						url = httpConn.getURL().toString();
						url = url.replaceAll("http://", "");
					}
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					// ex.printStackTrace();
					log.error("专项搜索任务>>地址"+url+"访问异常："+ex.getMessage());
					url = null;
				}

				if (url != null && !url.contains("baidu.com")) {
					// 获取标题
					title = e.html().replaceAll("<em>", "").replaceAll("</em>", "");

					Element summaryEle = element.select("div.c-abstract").first();

					if (summaryEle == null) {
						summaryEle = element.select("div > div > div.c-abstract").first();
					}

					summary = summaryEle.html().replaceAll("<em>", "").replaceAll("</em>", "").replaceAll("\n", "");
					
					netResult = new NmkSearchNetResult();
					netResult.setResultUrl(url);
					netResult.setResultTitle(title);
					netResult.setResultSummary(summary);
					netResult.setListId(item.getListId());
					netResult.setTaskId(item.getTaskId());
					netResult.setNetId(item.getNetId());
					netResult.setId(SearchUtils.getPrimaryIdByUUID());
					
					result.addEntResult(netResult);

					if(log.isDebugEnabled()){
						log.debug("专项任务("+item.getDomain()+")搜索结果：title:" + title + " url:" + url + " summary:" + summary + "\n");
					}
				}

			}
		}

		return result;
	}

	// public static void main(String[] args) {
	// BaiduResultAnalyzer analyzer = new BaiduResultAnalyzer();
	// analyzer.advancedSearch();
	// }

}
