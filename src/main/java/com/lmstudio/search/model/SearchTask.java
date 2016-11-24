package com.lmstudio.search.model;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the NMK_SEARCH_TASK database table.
 * 
 */
public class SearchTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private String taskId;

	private Date createDate;

	private String createUserid;

	private String createUsername;

	private String status;

	private String taskName;

	public SearchTask() {
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}