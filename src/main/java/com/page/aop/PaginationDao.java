package com.page.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dao层分页注解，用于控制数据分页
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PaginationDao {
}

