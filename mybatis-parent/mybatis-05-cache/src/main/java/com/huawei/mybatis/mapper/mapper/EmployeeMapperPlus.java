package com.huawei.mybatis.mapper.mapper;

import com.huawei.mybatis.mapper.entities.Employee;

import java.util.List;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/3
 * @since 1.0.0
 */
public interface EmployeeMapperPlus {

    Employee getSimpleEmpById(Integer id);

    Employee getComplicatedEmpById(Integer id);

    // 分步查询员工信息
    Employee getEmpByIdWithStep(Integer id);

    List<Employee> getEmpsByDeptId(Integer deptId);

}
