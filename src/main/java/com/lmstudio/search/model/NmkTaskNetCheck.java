package com.lmstudio.search.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the NMK_TASK_NET_CHECK database table.
 * 
 */
public class NmkTaskNetCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	private String listId;

	private String areaOrganId;

	private String auditOpinion;

	private Date crtTime;

	private String domain;

	private String entyName;

	private String entyType;

	private String goodsName;

	private String netId;

	private String serviceName;

	private BigDecimal status;

	private String taskId;

	private String taskName;

	private String tel;

	private String webSitName;
	
	
	private String includeAll;

	private String includeAny;
	
	private String notInclude;

	public NmkTaskNetCheck() {
	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getAreaOrganId() {
		return this.areaOrganId;
	}

	public void setAreaOrganId(String areaOrganId) {
		this.areaOrganId = areaOrganId;
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

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getNetId() {
		return this.netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWebSitName() {
		return this.webSitName;
	}

	public void setWebSitName(String webSitName) {
		this.webSitName = webSitName;
	}

	public String getIncludeAll() {
		return includeAll;
	}

	public void setIncludeAll(String includeAll) {
		this.includeAll = includeAll;
	}

	public String getIncludeAny() {
		return includeAny;
	}

	public void setIncludeAny(String includeAny) {
		this.includeAny = includeAny;
	}

	public String getNotInclude() {
		return notInclude;
	}

	public void setNotInclude(String notInclude) {
		this.notInclude = notInclude;
	}
	
	

}