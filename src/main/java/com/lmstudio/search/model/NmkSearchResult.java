/**
* TODO
* @Project: searchapp
* @Title: NmkSearchResult.java
* @Package com.lmstudio.search.model
* @author jason
* @Date 2016年12月2日 上午10:43:59
* @Copyright
* @Version 
*/
package com.lmstudio.search.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * @ClassName: NmkSearchResult
 * @author jason
 */
public class NmkSearchResult {

	private List<NmkSearchNetResult> list;

	public List<NmkSearchNetResult> getList() {
		return list;
	}

	public void setList(List<NmkSearchNetResult> list) {
		this.list = list;
	}

	public void addEntResult(NmkSearchNetResult netResult) {
		
		if (list == null) {
			list = new ArrayList<NmkSearchNetResult>();
		}
		list.add(netResult);
	}
}
