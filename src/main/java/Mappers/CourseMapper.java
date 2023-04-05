package Mappers;

import dto.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    List<Course> optionalSelect(@Param("select")String select, @Param("where")String where);
}
