package com.lmstudio.search.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the NMK_SEARCH_TASK_ENT database table.
 * 
 */
public class SearchTaskEnt implements Serializable {
	private static final long serialVersionUID = 1L;

	private String relId;

	private String entyId;

	private String entyName;

	private String entyType;

	private String taskId;

	public SearchTaskEnt() {
	}

	public String getRelId() {
		return this.relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
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

}