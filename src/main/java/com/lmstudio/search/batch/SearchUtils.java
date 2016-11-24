/**
* TODO
* @Project: searchapp
* @Title: SearchUtils.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月24日 下午4:40:04
* @Copyright
* @Version 
*/
package com.lmstudio.search.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 * 
 * @ClassName: SearchUtils
 * @author jason
 */
public class SearchUtils {

	/**
	 * 
	 * TODO
	 * 
	 * @Title: downLoadUrlHtml
	 * @param downLoadUrl
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String downLoadUrlHtml(String downLoadUrl, String encoding) throws IOException {
		String urlHtmlString = null;
		String temp = null;
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) new URL(downLoadUrl).openConnection();
			// 读取指定url的页面
			BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), encoding));
			temp = br.readLine();
			StringBuffer stringBuffer = new StringBuffer();
			// TODO delete
			while (temp != null) {
				stringBuffer.append(temp);
				stringBuffer.append("\n");
				temp = br.readLine();
			}
			urlHtmlString = stringBuffer.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			// do nothing
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// do nothing
		}

		return urlHtmlString;
	}
	
	public static String parseDomainOfUrl(String url) {
		String regex = "((https|http)://)?((\\w+\\.)+(\\w)+)/?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);

		String val = null;
		if (m.find()) {
			val = m.group(3);
		}

		return val;
	}
}
