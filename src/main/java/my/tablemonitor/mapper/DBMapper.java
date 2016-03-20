package my.tablemonitor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import my.tablemonitor.entity.SqlWrapper;

public interface DBMapper {

	@Select("${sql}")
	List<Map<String, ?>> executeSQL(SqlWrapper sql);
}
