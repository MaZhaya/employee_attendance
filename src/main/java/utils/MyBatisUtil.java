package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
    private static SqlSessionFactory factory;

    static {
        try {
            String resource = "mybatis-config.xml";
            factory = new SqlSessionFactoryBuilder()
                    .build(Resources.getResourceAsReader(resource));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return factory.openSession(true); // 自动提交
    }
}