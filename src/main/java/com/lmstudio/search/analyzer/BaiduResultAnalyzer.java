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
package com.lmstudio.search.analyzer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lmstudio.search.batch.SearchUtils;
import com.lmstudio.search.model.SearchTaskEnt;
import com.lmstudio.search.model.SearchTaskEntResult;
import com.lmstudio.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: BaiduResultAnalyzer
 * @author jason
 */
@Component(value="baiduResultAnalyzer")
public class BaiduResultAnalyzer implements ResultAnalyzer {

	private static Logger log = LoggerFactory.getLogger(BaiduResultAnalyzer.class);

	@Override
	public SearchTaskResult analyzer(String html, SearchTaskEnt item) {
		// TODO Auto-generated method stub
		SearchTaskResult result = new SearchTaskResult();
		
		Document doc = Jsoup.parse(html);
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
					
					log.debug("title:" + title + "\n url:" + url + "\n summary:" + summary + "\n");
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
				} catch (MalformedURLException ex) {
					// TODO Auto-generated catch block
					// ex.printStackTrace();
					url = null;
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					// ex.printStackTrace();
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

//	public static void main(String[] args) {
//		BaiduResultAnalyzer analyzer = new BaiduResultAnalyzer();
//		analyzer.advancedSearch();
//	}

}
