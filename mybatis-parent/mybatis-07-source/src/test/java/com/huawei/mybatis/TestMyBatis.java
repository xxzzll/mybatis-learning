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
     *  1. 获取SqlSessionFactory对象;
     *      解析文件中的每一个信息保存到Configuration对象中，返回包含Configuration的DefaultSqlSessionFactory对象
     *  2. 获取SqlSession对象;
     *      返回一个DefaultSqlSession对象，包含Executor和Configuration
     *      这一步会创建Executor
     *  3. 获取接口的代理对象（MapperProxy）;
     *      getMapper(type)，使用MapperProxyFactory创建一个MapperProxy代理对象。
     *      代理对象包含，DefaultSqlSession（Executor）
     *  4. 执行增删改查方法;
     *
     *  总结：
     *      1、根据配置文件（全局、sql映射）初始化Configuration对象。
     *      2、创建一个DefaultSqlSession对象。
     *          它里面包含Configuration以及
     *              Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
     *      3、DefaultSqlSession.getMapper(); 拿到mapper接口对应的MapperProxy
     *      4、MapperProxy里面有（DefaultSqlSession）
     *      5、执行增删改查方法：
     *          1）调用DefaultSqlSession的增删改查（Executor）
     *          2）会创建一个StatementHandler对象
     *              （同时也会创建出ParameterHandler和ResultSetHandler）
     *          3）调用StatementHandler预编译参数以及设置参数值
     *              使用ParameterHandler来给sql设置参数
     *          4）调用StatementHandler的增删改查方法
     *          5）ResultSetHandler封装结果
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
