package com.huawei.mybatis.mapper.mapper;

import com.huawei.mybatis.mapper.entities.Department;

/**
 * @author xixi
 * @Description：
 * @create 2020/3/4
 * @since 1.0.0
 */
public interface DeptmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdWithStep(Integer id);
}
