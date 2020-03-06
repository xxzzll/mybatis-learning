/**
 * FileName: Employee
 * Author:   xixi
 * Date:     19-8-1 下午8:44
 * Description: 员工实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.huawei.mybatis.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *  <br>
 * 〈员工实体类〉
 *
 * @author xixi
 * @create 19-8-1
 * @since 1.0.0
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;

}
