package com.lmstudio.search.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the NMK_CHECK_TASK database table.
 * 
 */
public class NmkCheckTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private String listId;

	private String auditOpinion;

	private Date crtTime;

	private String crtUser;

	private String crtUserName;

	private String includeAll;

	private String includeAny;

	private Date modTime;

	private String modUser;

	private String modUserName;

	private String notInclude;

	private String ruleId;

	private String ruleName;

	private BigDecimal status;

	private String taskName;

	private BigDecimal taskType;

	public NmkCheckTask() {
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getAuditOpinion() {
		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtUser() {
		return this.crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getCrtUserName() {
		return this.crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public String getIncludeAll() {
		return this.includeAll;
	}

	public void setIncludeAll(String includeAll) {
		this.includeAll = includeAll;
	}

	public String getIncludeAny() {
		return this.includeAny;
	}

	public void setIncludeAny(String includeAny) {
		this.includeAny = includeAny;
	}

	public Date getModTime() {
		return this.modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public String getModUser() {
		return this.modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getModUserName() {
		return this.modUserName;
	}

	public void setModUserName(String modUserName) {
		this.modUserName = modUserName;
	}

	public String getNotInclude() {
		return this.notInclude;
	}

	public void setNotInclude(String notInclude) {
		this.notInclude = notInclude;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public BigDecimal getTaskType() {
		return this.taskType;
	}

	public void setTaskType(BigDecimal taskType) {
		this.taskType = taskType;
	}

}