/**
* TODO
* @Project: searchapp
* @Title: SearchServiceImpl.java
* @Package com.lmstudio.search.analyzer
* @author jason
* @Date 2016年12月1日 下午6:27:50
* @Copyright
* @Version 
*/
package com.lmstudio.search.analyzer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmstudio.search.batch.SearchSettings;

/**
 * TODO
 * 
 * @ClassName: SearchServiceImpl
 * @author jason
 */
@Component
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchSettings searchSettins;

	@Override
	public String search(String url, SearchEngine se) throws Exception{
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();

		if (SearchEngine.BAIDU.equals(se)) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				HttpEntity entity1 = response1.getEntity();
				InputStream is = entity1.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String tmp = br.readLine();
				while (tmp != null) {
					sb.append(tmp).append("\n");
					tmp = br.readLine();
				}
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		}

		return sb.toString();
	}


}
