package my.tablemonitor;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import my.tablemonitor.service.TopicCheckerService;

public class Boot {
	public static void main(String[] args) throws Exception {

		Map<String, String> itemNameToSqlMap = new HashMap<String, String>();
		itemNameToSqlMap.put("test",
				"select count(*) as a from user_view_share where dt=${formatDate(nominalTime,'yyyyMMdd')} limit 10");

		List<String> thresholds = Arrays.asList("${test[0].a == 0}");

		Topic t = new Topic(1L, "test", "", itemNameToSqlMap, thresholds);

		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 17);

		Callback callback = new Callback() {

			@Override
			public void call(Map<String, List<Map<String, ?>>> sqlQueryResults,
					Map<String, Boolean> checkThresholdResults) {
				boolean pass = true;
				for (boolean result : checkThresholdResults.values()) {
					if (!result) {
						pass = false;
						break;
					}
				}

				if (!pass) {
					System.out.println("checkItemsResult");
					for (Entry<String, List<Map<String, ?>>> e : sqlQueryResults.entrySet()) {
						System.out.println("Item: " + e.getKey() + " Result: " + e.getValue());
					}

					System.out.println("checkThresholdsResult");
					for (Entry<String, Boolean> e : checkThresholdResults.entrySet()) {
						System.out.println("Expr: " + e.getKey() + " Result: " + e.getValue());
					}
				}
			}
		};

		TopicChecker tc = new TopicChecker(t, c.getTime(), callback);
		TopicCheckerService tcService = Services.get().get(TopicCheckerService.class);

		tcService.queue(tc);
	}
}
