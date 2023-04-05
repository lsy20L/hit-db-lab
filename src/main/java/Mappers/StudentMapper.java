package Mappers;


import dto.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Student> optionalSelect(@Param("select")String select, @Param("where")String where);
}
