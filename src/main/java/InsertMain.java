import Mappers.CourseMapper;
import Mappers.ScMapper;
import Mappers.StudentMapper;
import dto.Course;
import dto.SC;
import dto.Student;
import org.apache.ibatis.session.SqlSession;
import utils.MybatisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertMain {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        ScMapper scMapper = sqlSession.getMapper(ScMapper.class);
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
//        List<Course> courses = courseMapper.optionalSelect("*", "true");
//        Map<String,String> mss = new HashMap<>();
//        for(Course course:courses){
//            if(!mss.containsKey(course.getCnumber().substring(0,2))){
//                mss.put(course.getCnumber().substring(0,2),course.getCname());
//            }else{
//                if(!mss.get(course.getCnumber().substring(0,2)).equals(course.getCname())){
//                    System.out.println(course.getCname());
//                    System.out.println(course.getCnumber().substring(0,2));
//                    return;
//                }
//            }
//        }
//        mss.values().forEach(System.out::println);
//        System.out.println(mss.containsValue("46"));
//        System.out.println(mss.size());
        List<String> snumber = studentMapper.optionalSelect("Snumber", "Snumber like \"2023%\"").stream().map(student -> student.getSnumber()).collect(Collectors.toList());
        for(int i = 10;i<=25;++i){
            List<String> cnumber = courseMapper.optionalSelect("Cnumber", "Cnumber like \"" + i + "%\"").stream().map(Course::getCnumber).collect(Collectors.toList());
            if(cnumber.size()==0){
                System.out.println(i);
            }else{
                for(int j = 0;j<snumber.size();++j){
//                    System.out.println(snumber.get(j) + "," + cnumber.get(j % cnumber.size()) + "," + String.format("%.2f", (Math.random() * 60 + 40)));
                    scMapper.optioanlInsert(" ("+snumber.get(j) + "," + cnumber.get(j % cnumber.size()) + "," + String.format("%.2f", (Math.random() * 60 + 40))+") ");
                }
            }
        }
        MybatisUtil.commit(sqlSession);
        List<SC> scs = scMapper.optionalSelect("Cnumber", "true");
        System.out.println(scs.size());


    }
}
