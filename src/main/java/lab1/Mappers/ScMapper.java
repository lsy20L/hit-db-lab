package lab1.Mappers;


import lab1.dto.SC;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScMapper {
    List<SC> optionalSelect(@Param("select")String select, @Param("where")String where);
    boolean optioanlInsert(@Param("values")String values);
}
