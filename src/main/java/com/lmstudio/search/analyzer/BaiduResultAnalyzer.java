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
import java.net.URLEncoder;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lmstudio.search.model.SearchTaskResult;

/**
 * TODO
 * 
 * @ClassName: BaiduResultAnalyzer
 * @author jason
 */
public class BaiduResultAnalyzer implements ResultAnalyzer {

	@Override
	public List<SearchTaskResult> analyzer(String html) {
		// TODO Auto-generated method stub
		String keyword = "红旗连锁股份有限公司";
		String testurl = null;
		try {
			testurl = "http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&wd=" + URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Document doc = null;
		try {
			doc = Jsoup.connect(testurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements = doc.select("div#content_left");
		if(elements != null){
			elements = elements.first().children();
		}
		for (Element element : elements) {
			System.out.println(element.toString());
			String title = null;
			String url = null;
			String summary = null;
			if ((element.hasClass("result") || element.hasClass("result-op")) && element.hasClass("c-container")) {
				// 获取标题
				System.out.println(element.select("h3.t > a").first().html());
				title = element.select("h3.t > a").first().html().replaceAll("<em>", "").replaceAll("</em>", "");
			}

			System.out.println("title:" + title + "\n url:" + url + "\n summary:" + summary + "\n");
		}

		return null;
	}

//	public static void main(String[] args) {
//		BaiduResultAnalyzer analyzer = new BaiduResultAnalyzer();
//		analyzer.analyzer(null);
//	}

}
