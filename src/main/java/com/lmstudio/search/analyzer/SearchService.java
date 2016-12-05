/**
* TODO
* @Project: searchapp
* @Title: SearchService.java
* @Package com.lmstudio.search.analyzer
* @author jason
* @Date 2016年12月1日 下午6:25:12
* @Copyright
* @Version 
*/
package com.lmstudio.search.analyzer;

/**
* TODO
* @ClassName: SearchService
* @author jason
*/
public interface SearchService {

	String search(String url, SearchEngine se)throws Exception;
	
}
