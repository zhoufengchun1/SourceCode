package com.kangyh;

import com.kangyh.bean.Department;
import com.kangyh.dao.DepartmentMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: kangyh
 * @Date: 2019/7/29 22:52
 * @Description:
 */
public class MybatisTest
{
    @Test
    public void test() throws IOException
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession openSession = sessionFactory.openSession();
        DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
        Department department = mapper.getDeptByIdPlus(1);
        System.out.println(department);
        System.out.println(department.getEmps());
    }
}
