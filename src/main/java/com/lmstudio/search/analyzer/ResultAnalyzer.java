/**
* TODO
* @Project: searchapp
* @Title: ResultAnalyzer.java
* @Package com.lmstudio.search.batch
* @author jason
* @Date 2016年11月15日 下午4:15:37
* @Copyright
* @Version 
*/
package com.lmstudio.search.analyzer;

import java.util.List;

import com.lmstudio.search.model.SearchTaskResult;

/**
* TODO
* @ClassName: ResultAnalyzer
* @author jason
*/
public interface ResultAnalyzer {

	List<SearchTaskResult> analyzer(String html);
}
