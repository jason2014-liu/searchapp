package com.wondersgroup.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SearchTaskResult implements Serializable {
	
	/**
	* serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 1L;
	
	private List<SearchTaskEntResult> list;
	
	public SearchTaskResult() {
		super();
	}

	public List<SearchTaskEntResult> getList() {
		return list;
	}

	public void setList(List<SearchTaskEntResult> list) {
		this.list = list;
	}
	
	public void addEntResult(SearchTaskEntResult entResult){
		if(list == null){
			list = new ArrayList<SearchTaskEntResult>();
		}
		list.add(entResult);
	}
	
	

}