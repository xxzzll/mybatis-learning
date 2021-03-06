/**
 * FileName: EmployeeMapper
 * Author:   xixi
 * Date:     19-8-1 下午9:43
 * Description: EmployeeMapper接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.huawei.mybatis.config.mapper;

import com.huawei.mybatis.config.entities.Employee;

/**
 *  <br>
 * 〈EmployeeMapper接口〉
 *
 * @author xixi
 * @create 19-8-1
 * @since 1.0.0
 */
public interface EmployeeMapper {

    Employee getEmpById(Integer id);

}
