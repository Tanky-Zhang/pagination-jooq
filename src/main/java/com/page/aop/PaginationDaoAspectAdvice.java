package com.page.aop;

import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import com.page.PageContext;


/**
 * Dao层分页注解切面逻辑，用于控制数据分页
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Aspect
@Component
public class PaginationDaoAspectAdvice {

    @Around(value = "@within(com.page.aop.PaginationDao) || @annotation(com.page.aop.PaginationDao)")
    public Object paginationService(ProceedingJoinPoint jp) throws Throwable {
        PageContext context = PageContext.getContext();
        context.setPaginationDao(true);
        Object obj;
        try {
            obj = jp.proceed();
            //判断一次防止为空，造成不必要查询
            if (obj instanceof Collection && !((Collection<?>) obj).isEmpty()) {
                //看看是否在
                DSLContext dsl = context.getDslContext();
                //查询count之前将分页标识置为false，防止对监听器造成干扰
                context.setPaginationDao(false);
                String originalSql = context.getOriginalSql();
                String countSql = "(" + originalSql + ") as C";
                Integer count = dsl.selectCount().from(countSql).fetchOne().value1();
                context.setTotalCount(count);
            }
        } finally {
            context.setPaginationDao(false);
        }
        return obj;
    }
}
