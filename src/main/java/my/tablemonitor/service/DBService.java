package my.tablemonitor.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBService implements Service {

	private SqlSessionFactory sessionFactory;

	public DBService() throws IOException {
		String resource = "mybatis-config.xml";

		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		sessionFactory.getConfiguration().addMapper(DBMapper.class);
	}

	public interface DBMapper {

		@Select("${sql}")
		List<Map<String, ?>> executeSQL(SqlWrapper sql);
	}

	private interface Query<T> {
		T execute(DBMapper mapper);
	}

	class SqlWrapper {
		private String sql;

		public SqlWrapper(String sql) {
			super();
			this.sql = sql;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}
	}

	public List<Map<String, ?>> executeSQL(final String sql) {
		Query<List<Map<String, ?>>> q = new Query<List<Map<String, ?>>>() {

			@Override
			public List<Map<String, ?>> execute(DBMapper mapper) {
				return mapper.executeSQL(new SqlWrapper(sql));
			}

		};

		return execute(q);
	}

	private <T> T execute(Query<T> q) {
		SqlSession session = sessionFactory.openSession();
		DBMapper mapper = session.getMapper(DBMapper.class);
		T ret = q.execute(mapper);
		session.close();
		return ret;
	}
}