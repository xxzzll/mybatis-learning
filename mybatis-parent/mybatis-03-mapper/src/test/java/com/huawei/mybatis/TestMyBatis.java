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

import com.huawei.mybatis.mapper.entities.Department;
import com.huawei.mybatis.mapper.entities.Employee;
import com.huawei.mybatis.mapper.mapper.DeptmentMapper;
import com.huawei.mybatis.mapper.mapper.EmployeeAnnotationMapper;
import com.huawei.mybatis.mapper.mapper.EmployeeMapper;
import com.huawei.mybatis.mapper.mapper.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            Employee emp = employeeMapper.getEmpById(3);
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

    /**
     * 测试增删改
     * 1、mybatis允许增删改直接定义以下类型返回值
     *      Integer、Long、Boolean、void
     * 2、我们需要手动提交事务
     * 3、获取自增主键值
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

//            List<Employee> employees = mapper.getEmpByLastNameLike("%t%");
//            for (Employee employee : employees) {
//                System.out.println(employee);
//            }

            Map<String, Object> returnMap = mapper.getEmpByIdReturnMap(3);
            System.out.println(returnMap);

            Map<String, Employee> empByLastNameLikeReturnMap = mapper.getEmpByLastNameLikeReturnMap("%t%");
            System.out.println(empByLastNameLikeReturnMap);

            // 测试添加
//            Employee employee = new Employee(null, "tom3", null, 1);
//            System.out.println(mapper);
//            mapper.addEmp(employee);


            // 测试更新
//            Employee employee2 = new Employee(3, "tom", "tom@163.com", 0);
//            mapper.updateEmp(employee2);

            // 测试删除
//            Long aLong = mapper.deleteEmpById(1);
//            System.out.println(aLong);

            // 提交事务
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }

    /**
     * mapper定义多个参数的接口测试
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

            Employee employee = mapper.getEmpByIdAndLastName(3, "tom");
            System.out.println(employee);

            Map<String, Object> map = new HashMap<>();
            map.put("id", 3);
            map.put("lastName", "tom");
//            Employee employee = mapper.getEmpByMap(map);
//            System.out.println(employee);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void test6() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
//            Employee simpleEmp = mapper.getSimpleEmpById(3);
//            Employee complicatedEmp = mapper.getComplicatedEmpById(3);
//            System.out.println(simpleEmp);
//            System.out.println(complicatedEmp);

            Employee stepEmp = mapper.getEmpByIdWithStep(3);
            System.out.println(stepEmp);
//            System.out.println(stepEmp.getDept().getDepartmentName());
        }finally {
            sqlSession.close();
        }
    }


    /***
     * 测试 resultMap中collection标签的使用
     * @throws IOException
     */
    @Test
    public void test7() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            DeptmentMapper mapper = sqlSession.getMapper(DeptmentMapper.class);
//            Department department = mapper.getDeptByIdPlus(1);
//            System.out.println(department);

            Department deptByIdWithStep = mapper.getDeptByIdWithStep(1);
            System.out.println(deptByIdWithStep.getDepartmentName());
            System.out.println(deptByIdWithStep.getEmps());
        }finally {
            sqlSession.close();
        }
    }
}
