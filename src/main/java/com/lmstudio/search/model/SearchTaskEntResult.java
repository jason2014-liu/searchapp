/**
* TODO
* @Project: searchapp
* @Title: SearchTaskEntResult.java
* @Package com.lmstudio.search.model
* @author jason
* @Date 2016年11月22日 下午5:38:32
* @Copyright
* @Version 
*/
package com.lmstudio.search.model;

import java.io.Serializable;

/**
* TODO
* @ClassName: SearchTaskEntResult
* @author jason
*/
public class SearchTaskEntResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String entyName;

	private String keyword;

	private String relId;

	private String website;

	public SearchTaskEntResult() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntyName() {
		return this.entyName;
	}

	public void setEntyName(String entyName) {
		this.entyName = entyName;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getRelId() {
		return this.relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
