/**
 * FileName: TestMyBatis
 * Author:   xixi
 * Date:     19-8-1 下午9:18
 * Description: 测试Mybatis
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.huawei.mybatis;

import com.huawei.mybatis.entities.Employee;
import com.huawei.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author xixi
 * @create 19-8-1
 * @since 1.0.0
 */
public class TestMyBatis {

    /**
     * 公共获取SqlSessionFactory方法
     * @return
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }


    /**
     * MyBatis源码分析：
     *  1. 获取SqlSessionFactory对象：
     *  2. 获取SqlSession对象：
     *  3. 获取接口的代理对象（MapperProxy）：
     *      getMapper(type)，使用MapperProxyFactory创建一个MapperProxy代理对象。
     *      代理对象包含，DefaultSqlSession（Executor）
     *  4. 执行增删改查方法：
     *
     *
     * @throws IOException
     */
    @Test
    public void testSource() throws IOException {
        // 1、获取SqlSessionFactory、SqlSession
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            // 2、获取接口的实现类对象
            // 为接口自动创建一个代理对象，代理对象去执行增删改查
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            System.out.println(employeeMapper); // org.apache.ibatis.binding.MapperProxy@6737fd8f,接口的代理对象

            Employee emp = employeeMapper.getEmp(3);
            System.out.println(emp);
        }finally {
            sqlSession.close();
        }
    }
}
