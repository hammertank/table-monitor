package my.tablemonitor.topic;

import java.util.List;
import java.util.Map;

public class Topic {
	private long id;
	private String name;
	private String desc;
	private Map<String, String> items;
	private List<String> thresholds;

	public Topic(long id, String name, String desc, Map<String, String> items,
			List<String> thresholds) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.items = items;
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

	public Map<String, String> getItems() {
		return items;
	}

	public void setItems(Map<String, String> items) {
		this.items = items;
	}

	public List<String> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<String> thresholds) {
		this.thresholds = thresholds;
	}

}
