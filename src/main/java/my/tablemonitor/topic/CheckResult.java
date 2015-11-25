package my.tablemonitor.topic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CheckResult {

	private String topicName;
	private Boolean pass;
	private Map<String, List<Map<String, ?>>> itemsResult;
	private Map<String, Boolean> thresholdResult;
	private Timestamp timestamp;

	public CheckResult(String tn, boolean p,
			Map<String, List<Map<String, ?>>> ir, Map<String, Boolean> tr,
			Timestamp ts) {
		topicName = tn;
		pass = p;
		itemsResult = ir;
		thresholdResult = tr;
		timestamp = ts;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Boolean getPass() {
		return pass;
	}

	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	public Map<String, List<Map<String, ?>>> getItemsResult() {
		return itemsResult;
	}

	public void setItemsResult(Map<String, List<Map<String, ?>>> itemsResult) {
		this.itemsResult = itemsResult;
	}

	public Map<String, Boolean> getThresholdResult() {
		return thresholdResult;
	}

	public void setThresholdResult(Map<String, Boolean> thresholdResult) {
		this.thresholdResult = thresholdResult;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
