/**
 * FileName: EmployeeMapper
 * Author:   xixi
 * Date:     19-8-1 下午9:43
 * Description: EmployeeMapper接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.huawei.mybatis.mapper.mapper;

import com.huawei.mybatis.mapper.entities.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  <br>
 * 〈EmployeeMapper接口〉
 *
 * @author xixi
 * @create 19-8-1
 * @since 1.0.0
 */
public interface EmployeeMapper {

    // 多条记录封装一个map，Map<Integer, Employee>：键是记录的主键，值是一条记录
    // 告诉mybatis封装map时使用哪个属性作为key
    @MapKey("lastName")
    Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);

    // 返回一条记录的map，key为列名，value为列的值
    Map<String, Object> getEmpByIdReturnMap(Integer id);

    List<Employee> getEmpByLastNameLike(String lastName);

    Employee getEmpByMap(Map<String, Object> map);

    Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    boolean updateEmp(Employee employee);

    Long deleteEmpById(Integer id);
}
