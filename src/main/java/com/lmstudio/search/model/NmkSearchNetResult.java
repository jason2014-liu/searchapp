package com.lmstudio.search.model;

import java.io.Serializable;

/**
 * The persistent class for the NMK_SEARCH_NET_RESULT database table.
 * 
 */
public class NmkSearchNetResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String domain;

	private String listId;

	private String netId;

	private String resultKeyword;

	private String resultSummary;

	private String resultTitle;

	private String resultUrl;

	private String taskId;
	


	public NmkSearchNetResult() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getNetId() {
		return this.netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getResultKeyword() {
		return this.resultKeyword;
	}

	public void setResultKeyword(String resultKeyword) {
		this.resultKeyword = resultKeyword;
	}

	public String getResultSummary() {
		return this.resultSummary;
	}

	public void setResultSummary(String resultSummary) {
		this.resultSummary = resultSummary;
	}

	public String getResultTitle() {
		return this.resultTitle;
	}

	public void setResultTitle(String resultTitle) {
		this.resultTitle = resultTitle;
	}

	public String getResultUrl() {
		return this.resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


}