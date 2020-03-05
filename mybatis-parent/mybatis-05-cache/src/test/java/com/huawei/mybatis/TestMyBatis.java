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
import com.huawei.mybatis.mapper.mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


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
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * MyBatis两级缓存：
     *  一级缓存：（本地缓存） SqlSession级别的缓存，一级缓存是一直开启的。sqlSession级别的一个Map
     *      与数据库同一次会话期间查询到的数据会放在本地缓存中
     *      以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
     *
     *      一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需再向数据库发出查询）
     *      1）sqlSession不同。
     *      2）sqlSession相同，查询条件不同。（当前一级缓存中没有这个数据）
     *      3）sqlSession相同，两次查询之间执行了增删改操作（这次增删改有可能对当前数据有影响）
     *      4）sqlSession相同，手动清除一级缓存（缓存清空）
     *
     *  二级缓存：（全局缓存） 基于namespace级别的缓存，一个namespace对应一个二级缓存
     *      工作机制：
     *      1）一个会话，查询一条语句，这个数据会被放在当前会话的一级缓存中
     *      2）如果会话关闭，一级缓存中的数据会被保存到二级缓存中; 新的会话查询信息，就可以参照二级缓存。
     *      3）sqlSession===> EmployeeMapper===>Employee
     *                        DeptmentMapper===>Department
     *         不同namespace查出的数据会放在自己对应的缓存中（Map中）
     *
     *       效果：会从二级缓存中获取
     *          查出的数据都被默认放在一级缓存中。
     *          只有会话提交或关闭之后，一级缓存中的数据才会转移到二级缓存中。
     *
     *      使用：
     *          1）开启全局二级缓存配置：<setting name="的cacheEnable：" value="true"/>
     *          2）去mapper.xml中配置使用二级缓存：
     *              <cache></cache>
     *          3）我们的POJO需要实现序列化接口
     *
     *  和缓存有关的设置/属性
     *      1）cacheEnabled=true, false：关闭缓存（二级缓存）（一级缓存一直可用）
     *      2）每个select标签都有 useCache="true":
     *          false：不使用缓存（一级缓存依然可用，二级缓存不能被使用）
     *      3）每个增删改标签的：flushCache="true":[一、二级缓存都会被清空]
     *          增删改执行完后就会清空缓存;
     *          测试：flushCache="true"： 一级缓存被清空，二级缓存也被清空;
     *          查询标签：flushCache="false"：
     *              如果flushCache="true"，每次查询之后都会被清空缓存，缓存是没有被使用的;
     *     4）sqlSession.clearCache();只能清除当前session的一级缓存;
     *     5）localCacheScope=SESSION（默认） | STATEMENT：本地缓存作用域（一级缓存session），当前会话的所有数据被清空。
     *              STATEMENT：可以达到禁用缓存的目地
     *  定义二级缓存的接口 Cache(org.apache.ibatis.cache.Cache)
     *
     *  第三方缓存整合：
     *      eg：ehcache
     *      1. 导入第三方缓存包
     *      2. 导入与第三方缓存整合的适配包，MyBatis官方有
     *      3. mapper.xml中使用自定义缓存：
     *          <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     */
    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(3);
            System.out.println(emp);

//            Employee emp2 = mapper.getEmpById(3);
//            System.out.println(emp2);

            // 1）sqlSession不同
//            SqlSession sqlSession2 = sqlSessionFactory.openSession();
//            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
//            Employee emp2 = mapper2.getEmpById(3);
//            System.out.println(emp2);

            // 2）sqlSession相同，查询条件不同。（当前一级缓存中没有这个数据）
//            Employee emp2 = mapper.getEmpById(4);
//            System.out.println(emp2);

            // 3）sqlSession相同，两次查询之间执行了增删改操作（这次增删改有可能对当前数据有影响）
//            mapper.updateEmp(new Employee(4, "tom_modify", "tom_modify@163.com", 1, new Department(1)));

            // 4）sqlSession相同，手动清除一级缓存（缓存清空）
//            sqlSession.clearCache();

            Employee emp2 = mapper.getEmpById(3);
            System.out.println(emp2);

            System.out.println(emp == emp2);

        }finally {
            sqlSession.close();
        }
    }


    @Test
    public void testSecoundLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            Employee emp01 = mapper.getEmpById(3);
            System.out.println(emp01);

//            sqlSession.clearCache();
            sqlSession.close();

            // 插入增删改
//            mapper2.updateEmp(new Employee(3, "tom", "tom@163.com", 1, new Department(1)));

            Employee emp02 = mapper2.getEmpById(3);
            System.out.println(emp02);

//            sqlSession.close();
            sqlSession2.close();
        }finally {
            sqlSession.close();
            sqlSession2.close();
        }
    }
}
