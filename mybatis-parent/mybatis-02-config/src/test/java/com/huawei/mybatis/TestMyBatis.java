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

import com.huawei.mybatis.config.entities.Employee;
import com.huawei.mybatis.config.mapper.EmployeeAnnotationMapper;
import com.huawei.mybatis.config.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 *  <br>
 * 1、接口式编程
 *  原生：     XxxDao      ====> XxxDaoImpl
 *  mybatis:  XxxMapper   ====> XxxMapper.xml
 * 2、SqlSession代表和数据库的一次会话，用完必须关闭;
 * 3、SqlSession和connection一样它们都是非线程安全的，每次使用都应该重新获取;
 * 4、mapper接口没有实现类，但mybatis为这个接口生成一个代理对象;
 *          将接口和xml文件绑定;
 *          EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的文件
 *      mybatis全局配置文件，包含数据库连接池信息、事务管理器等...系统运行环境信息
 *      sql映射文件，保存了每个sql语句的映射信息;
 *      将sql文件抽取出来;
 *
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
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    @Test
    public void test1() throws IOException {
        SqlSession openSession = null;
        try {
            openSession = getSqlSessionFactory().openSession();
            Employee employee = openSession.selectOne("com.huawei.mybatis.selectEmp", 1);
            System.out.println(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(openSession != null)
                openSession.close();
        }
    }

    @Test
    public void test2() throws IOException {
        SqlSession openSession = null;
        try{
            // 1、获取SqlSessionFactory和SqlSession
            openSession = getSqlSessionFactory().openSession();
            // 2、获取接口的实现类对象
            // 为接口自动创建一个代理对象，代理对象去执行增删改查
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
            System.out.println(employeeMapper); // org.apache.ibatis.binding.MapperProxy@6737fd8f,接口的代理对象
            Employee emp = employeeMapper.getEmpById(1);
            System.out.println(emp);
        }finally {
            if(openSession != null)
                openSession.close();
        }
    }

    @Test
    public void test3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeAnnotationMapper mapper = sqlSession.getMapper(EmployeeAnnotationMapper.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(emp);
        }finally {
            sqlSession.close();
        }
    }
}
