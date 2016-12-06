/**
* TODO
* @Project: searchapp
* @Title: SearchEngine.java
* @Package com.lmstudio.search.analyzer
* @author jason
* @Date 2016年11月24日 上午10:05:41
* @Copyright
* @Version 
*/
package com.wondersgroup.search.analyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 
 * @ClassName: SearchEngine
 * @author jason
 */
public enum SearchEngine {

	BAIDU("Baidu"), SOUGOU("Sougou"), GOOGLE("Google"), Bing("Bing");

	private final String engineName;

	private SearchEngine(String engineName) {
		this.engineName = engineName;
	}

	public String getEngineName() {
		return engineName;
	}

	private static final Map<String, SearchEngine> nameMap;

	static {
		nameMap = new HashMap<String, SearchEngine>();
		for (SearchEngine engine : SearchEngine.values()) {
			nameMap.put(engine.getEngineName(), engine);
		}
	}

}
