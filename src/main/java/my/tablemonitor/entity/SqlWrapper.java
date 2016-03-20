package my.tablemonitor.entity;

public class SqlWrapper {
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