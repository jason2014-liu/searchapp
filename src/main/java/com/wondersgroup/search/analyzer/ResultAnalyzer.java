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
package com.wondersgroup.search.analyzer;

import com.wondersgroup.search.model.NmkSearchResult;
import com.wondersgroup.search.model.NmkTaskNetCheck;
import com.wondersgroup.search.model.SearchTaskEnt;
import com.wondersgroup.search.model.SearchTaskResult;

/**
* TODO
* @ClassName: ResultAnalyzer
* @author jason
*/
public interface ResultAnalyzer {

	/**
	* TODO 主体搜索
	* @Title: analyzer
	* @param html
	* @param item
	* @return
	 */
	SearchTaskResult analyzer(String html, SearchTaskEnt item);
	
	/**
	* TODO 專項高級搜索
	* @Title: analyzer
	* @param html
	* @param item
	* @return
	 */
	NmkSearchResult analyzer(String html, NmkTaskNetCheck item);
}
