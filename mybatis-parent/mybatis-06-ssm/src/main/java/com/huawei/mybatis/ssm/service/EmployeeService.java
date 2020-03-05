package com.huawei.mybatis.ssm.service;

import com.huawei.mybatis.ssm.entities.Employee;
import com.huawei.mybatis.ssm.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/5
 * @since 1.0.0
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getEmps() {
        return employeeMapper.getEmps();
    }
}
