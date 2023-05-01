package lab1.Mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoMapper {
    List<Map<String,Object>> optionalSelect(@Param("sql")String sql);
}
