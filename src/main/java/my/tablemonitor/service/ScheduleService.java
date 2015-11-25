package my.tablemonitor.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import my.tablemonitor.topic.TopicChecker;

public class ScheduleService implements Service {

	private final DelayQueue<TopicChecker> queue = new DelayQueue<TopicChecker>();

	private int threads = 5;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(threads,
			threads, 10, TimeUnit.SECONDS, (BlockingQueue) queue);

	public synchronized void queue(TopicChecker d) {
		if (d == null) {
			return;
		}

		if (!executor.isShutdown()) {
			executor.execute(d);
		}
	}
}
