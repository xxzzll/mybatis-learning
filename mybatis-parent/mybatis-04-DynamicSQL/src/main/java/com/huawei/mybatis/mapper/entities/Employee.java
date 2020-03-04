/**
 * FileName: Employee
 * Author:   xixi
 * Date:     19-8-1 下午8:44
 * Description: 员工实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.huawei.mybatis.mapper.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;


/**
 *  <br>
 * 〈员工实体类〉
 *
 * @author xixi
 * @create 19-8-1
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Alias("emp")
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;

    private Department dept; // 关联属性

}
