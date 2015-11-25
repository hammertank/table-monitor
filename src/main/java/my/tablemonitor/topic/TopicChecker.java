package my.tablemonitor.topic;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.oozie.util.DateUtils;
import org.apache.oozie.util.ELEvaluator;
import org.apache.oozie.util.ELEvaluator.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.tablemonitor.service.DBService;
import my.tablemonitor.service.Services;

public class TopicChecker implements Runnable, Delayed {

	private static final Logger log = LoggerFactory.getLogger(TopicChecker.class);

	private long nominalTime;

	private Topic topic;

	private ELEvaluator evaluator;

	private DBService dbService = Services.get().get(DBService.class);

	private Map<String, List<Map<String, ?>>> checkItemsResult;

	private Map<String, Boolean> checkThresholdsResult;

	private Callback callback;

	public TopicChecker(Topic t, Date d, Callback c) {
		topic = t;
		nominalTime = d.getTime();
		callback = c;
	}

	@Override
	public int compareTo(Delayed o) {
		long diff = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
		if (diff > 0) {
			return 1;
		} else if (diff < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(nominalTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		try {
			initEvaluator();
			checkItemsResult = checkItems();
			updateEvaluator();
			checkThresholdsResult = checkThresholds();
			callback.call(checkItemsResult, checkThresholdsResult);;
		} catch (Exception e) {
			log.error("Error occurred when check topic[" + topic.getName() + "].", e);
		}
	}

	private Map<String, List<Map<String, ?>>> checkItems() throws Exception {
		Map<String, List<Map<String, ?>>> ret = new HashMap<String, List<Map<String, ?>>>();
		for (Entry<String, String> entry : topic.getItems().entrySet()) {
			String sql = evaluator.evaluate(entry.getValue(), String.class);
			List<Map<String, ?>> result = dbService.executeSQL(sql);
			ret.put(entry.getKey(), result);
		}
		return ret;
	}

	private Map<String, Boolean> checkThresholds() throws Exception {

		Map<String, Boolean> ret = new HashMap<String, Boolean>();

		for (String expr : topic.getThresholds()) {
			boolean result = false;
			try {
				result = evaluator.evaluate(expr, Boolean.class);
			} catch (Exception e) {
				log.warn("Error occurred when checkThresholds. topic=" + topic.getName() + " threshold=" + expr, e);

			} finally {
				ret.put(expr, result);
			}
		}

		return ret;
	}

	private void initEvaluator() throws NoSuchMethodException, SecurityException {
		evaluator = new ELEvaluator();
		evaluator.getContext().setVariable("nominalTime", new Date(nominalTime));
		evaluator.getContext().addFunction("", "formatDate",
				DateUtils.class.getDeclaredMethod("formatDateCustom", Date.class, String.class));

	}

	private void updateEvaluator() {

		Context context = evaluator.getContext();

		for (Entry<String, List<Map<String, ?>>> entry : checkItemsResult.entrySet()) {
			context.setVariable(entry.getKey(), entry.getValue());
		}
	}
}
