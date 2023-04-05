import Mappers.NoMapper;
import org.apache.ibatis.session.SqlSession;
import utils.MybatisUtil;

import java.util.List;
import java.util.Map;

public class SelectMain {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        NoMapper mapper = sqlSession.getMapper(NoMapper.class);
        List<Map<String, Object>> maps = mapper.optionalSelect("123");
        for(Map<String,Object> map:maps){
            for(String key:map.keySet()){
                System.out.println(key+":"+map.get(key));
            }
        }

    }
}
