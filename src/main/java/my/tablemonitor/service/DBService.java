package my.tablemonitor.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import my.tablemonitor.entity.SqlWrapper;
import my.tablemonitor.mapper.DBMapper;

public class DBService implements Service {

	private SqlSessionFactory sessionFactory;

	public DBService() throws IOException {
		String resource = "mybatis-config.xml";

		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		sessionFactory.getConfiguration().addMapper(DBMapper.class);
	}

	public List<Map<String, ?>> executeSQL(final String sql) {
		SqlSession session = sessionFactory.openSession();
		DBMapper mapper = session.getMapper(DBMapper.class);
		List<Map<String, ?>> ret = mapper.executeSQL(new SqlWrapper(sql));
		session.close();
		return ret;
	}
}
