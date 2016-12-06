/**
* TODO
* @Project: searchapp
* @Title: SearchSettings.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月24日 下午2:58:44
* @Copyright
* @Version 
*/
package com.wondersgroup.search.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* TODO
* @ClassName: SearchSettings
* @author jason
*/
@ConfigurationProperties(prefix="search",locations="classpath:search.properties")
public class SearchSettings {

	private String engine;
	private String baiduUrl;
	private int resultCount;
	
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getBaiduUrl() {
		return baiduUrl;
	}
	public void setBaiduUrl(String baiduUrl) {
		this.baiduUrl = baiduUrl;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	
	
	
}
