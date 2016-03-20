package my.tablemonitor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CheckResult {

	private String topicName;
	private Boolean pass;
	private Map<String, List<Map<String, ?>>> sqlQueryResults;
	private Map<String, Boolean> checkThresholdResults;
	private Timestamp timestamp;

	public CheckResult(String tn, boolean p,
			Map<String, List<Map<String, ?>>> ir, Map<String, Boolean> tr,
			Timestamp ts) {
		topicName = tn;
		pass = p;
		sqlQueryResults = ir;
		checkThresholdResults = tr;
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

	public Map<String, List<Map<String, ?>>> getSqlQueryResults() {
		return sqlQueryResults;
	}

	public void setSqlQueryResults(Map<String, List<Map<String, ?>>> itemsResult) {
		this.sqlQueryResults = itemsResult;
	}

	public Map<String, Boolean> getCheckThresholdResult() {
		return checkThresholdResults;
	}

	public void setCheckThresholdResult(Map<String, Boolean> thresholdResult) {
		this.checkThresholdResults = thresholdResult;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
