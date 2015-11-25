package my.tablemonitor;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import my.tablemonitor.service.TopicCheckerService;
import my.tablemonitor.service.Services;
import my.tablemonitor.topic.Callback;
import my.tablemonitor.topic.Topic;
import my.tablemonitor.topic.TopicChecker;

public class Boot {
	public static void main(String[] args) throws Exception {

		Map<String, String> items = new HashMap<String, String>();
		items.put("test",
				"select count(*) as a from user_view_share where dt=${formatDate(nominalTime,'yyyyMMdd')} limit 10");

		List<String> thresholds = Arrays.asList("${test[0].a == 0}");

		Topic t = new Topic(1L, "test", "", items, thresholds);

		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 17);

		Callback callback = new Callback() {

			@Override
			public void call(Map<String, List<Map<String, ?>>> checkItemsResult,
					Map<String, Boolean> checkThresholdsResult) {
				boolean pass = true;
				for (boolean result : checkThresholdsResult.values()) {
					if (!result) {
						pass = false;
						break;
					}
				}

				if (!pass) {
					System.out.println("checkItemsResult");
					for(Entry<String, List<Map<String, ?>>> e : checkItemsResult.entrySet()) {
						System.out.println("Item: " + e.getKey() +" Result: " + e.getValue());
					}
					
					System.out.println("checkThresholdsResult");
					for(Entry<String, Boolean> e : checkThresholdsResult.entrySet()) {
						System.out.println("Expr: " + e.getKey() +" Result: " + e.getValue());
					}
				}
			}
		};

		TopicChecker tc = new TopicChecker(t, c.getTime(), callback);
		TopicCheckerService tcService = Services.get().get(TopicCheckerService.class);

		tcService.queue(tc);
	}
}
