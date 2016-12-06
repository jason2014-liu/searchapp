package com.wondersgroup.search.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the NMK_SEARCH_TASK_ENT database table.
 * 
 */
public class SearchTaskEnt implements Serializable {
	private static final long serialVersionUID = 1L;

	private String priId;

	private String entyId;

	private String entyName;

	private String entyType;

	private String taskId;

	private String website;

	/**
	 * 对应数据库不存此字段
	 */
	private String taskType;

	public SearchTaskEnt() {
	}

	public String getPriId() {
		return priId;
	}

	public void setPriId(String priId) {
		this.priId = priId;
	}

	public String getEntyId() {
		return this.entyId;
	}

	public void setEntyId(String entyId) {
		this.entyId = entyId;
	}

	public String getEntyName() {
		return this.entyName;
	}

	public void setEntyName(String entyName) {
		this.entyName = entyName;
	}

	public String getEntyType() {
		return this.entyType;
	}

	public void setEntyType(String entyType) {
		this.entyType = entyType;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

}