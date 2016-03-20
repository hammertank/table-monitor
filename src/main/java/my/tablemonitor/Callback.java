package my.tablemonitor;

import java.util.List;
import java.util.Map;

public interface Callback {
	public void call(Map<String, List<Map<String, ?>>> sqlQueryResults, Map<String, Boolean> checkThresholdResults);
}
