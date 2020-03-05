package com.huawei.mybatis.ssm.controller;

import com.huawei.mybatis.ssm.entities.Employee;
import com.huawei.mybatis.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/5
 * @since 1.0.0
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("getEmps")
    public String getEmps(Map<String,Object> map){
        List<Employee> emps = employeeService.getEmps();
        map.put("allEmps", emps);
        return "list";
    }
}
