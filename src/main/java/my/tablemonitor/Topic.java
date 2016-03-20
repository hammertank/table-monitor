package my.tablemonitor;

import java.util.List;
import java.util.Map;

public class Topic {
	private long id;
	private String name;
	private String desc;
	private Map<String, String> itemNameToSqlMap;
	private List<String> thresholds;

	public Topic(long id, String name, String desc, Map<String, String> itemNameToSqlMap, List<String> thresholds) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.itemNameToSqlMap = itemNameToSqlMap;
		this.thresholds = thresholds;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, String> getItemNameToSqlMap() {
		return itemNameToSqlMap;
	}

	public void setItemNameToSqlMap(Map<String, String> items) {
		this.itemNameToSqlMap = items;
	}

	public List<String> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<String> thresholds) {
		this.thresholds = thresholds;
	}

}
