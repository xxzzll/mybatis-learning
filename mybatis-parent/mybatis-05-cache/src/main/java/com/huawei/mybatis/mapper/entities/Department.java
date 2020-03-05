package com.huawei.mybatis.mapper.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/4
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Department {
    private Integer id;
    private String departmentName;
    private List<Employee> emps; // 结果集

    public Department(Integer id) {
        this.id = id;
    }
}
