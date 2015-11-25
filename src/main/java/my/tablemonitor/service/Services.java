package my.tablemonitor.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Services {

	private static Services SERVICES;

	static {
		try {
			SERVICES = new Services();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<Class, Service> services = new HashMap<Class, Service>();

	private Services() throws Exception {
		try {
			services.put(DBService.class, new DBService());
			services.put(ScheduleService.class, new ScheduleService());
		} catch (IOException e) {
			throw new Exception("Error occurred when construct instance of Services.", e);
		}
	}

	public static Services get() {
		return SERVICES;
	}

	@SuppressWarnings("unchecked")
	public <T extends Service> T get(Class<T> clazz) {
		return (T) services.get(clazz);
	}
}
