package my.tablemonitor.topic;

import java.util.List;
import java.util.Map;

public interface Callback {
	public void call(Map<String, List<Map<String, ?>>> checkItemsResult, Map<String, Boolean> checkThresholdsResult);
}
