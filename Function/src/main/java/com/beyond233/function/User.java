package com.beyond233.function;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 1:06
 */
@Data
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String name;
    private int age;

}
