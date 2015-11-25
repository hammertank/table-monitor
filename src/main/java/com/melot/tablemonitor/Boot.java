package com.melot.tablemonitor;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.melot.tablemonitor.service.ScheduleService;
import com.melot.tablemonitor.service.Services;
import com.melot.tablemonitor.topic.Topic;
import com.melot.tablemonitor.topic.TopicChecker;

public class Boot {
	public static void main(String[] args) throws Exception {
		Services.init();

		Map<String, String> items = new HashMap<String, String>();
		items.put("test",
				"select count(*) as a from user_view_share where dt=${formatDate(nominalTime,'yyyyMMdd')} limit 10");

		List<String> thresholds = Arrays.asList("${test[0].a > 0}");

		Topic t = new Topic(1L, "test", "", items, thresholds);
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 17);

		TopicChecker tc = new TopicChecker(t, c.getTime());
		ScheduleService scheduleService = Services.get().get(
				ScheduleService.class);

		scheduleService.queue(tc);
	}
}
