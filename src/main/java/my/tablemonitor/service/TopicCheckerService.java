package my.tablemonitor.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import my.tablemonitor.TopicChecker;

public class TopicCheckerService implements Service {

	private int threads = 5;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 10, TimeUnit.SECONDS,
			(BlockingQueue) new DelayQueue<TopicChecker>());

	public synchronized void queue(TopicChecker d) {
		if (d == null) {
			return;
		}

		if (!executor.isShutdown()) {
			executor.execute(d);
		}
	}
}
