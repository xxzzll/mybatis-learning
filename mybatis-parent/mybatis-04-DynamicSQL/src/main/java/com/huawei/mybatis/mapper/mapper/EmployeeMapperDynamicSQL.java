package com.huawei.mybatis.mapper.mapper;

import com.huawei.mybatis.mapper.entities.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/4
 * @since 1.0.0
 */
public interface EmployeeMapperDynamicSQL {

    // 多条件查询
    List<Employee> getEmpsByConditionsIf(Employee employee);

    List<Employee> getEmpsByConditionsTrim(Employee employee);

    List<Employee> getEmpsByConditionsChoose(Employee employee);

    void updateEmpSet(Employee employee);

    List<Employee> getEmpsByConditionsForeach(@Param("ids") List<Integer> ids);

    void batchSaveEmpsForeach(@Param("emps") List<Employee> emps);
}
