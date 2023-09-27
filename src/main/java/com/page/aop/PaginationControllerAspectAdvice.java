package com.page.aop;

import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.page.PageContext;
import com.page.response.PageMessage;
import com.page.request.PageReqInfo;
import com.page.response.PageRespData;

import lombok.extern.slf4j.Slf4j;


/**
 * Controller层分页注解切面逻辑，用于控制数据分页
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Component
@Aspect
@SuppressWarnings({"rawtypes"})
@Slf4j
public class PaginationControllerAspectAdvice {

    @Around(value = "@within(com.page.aop.PaginationController) || @annotation(com.page.aop.PaginationController)")
    public Object paginationController(ProceedingJoinPoint jp) throws Throwable {
        PageContext context = PageContext.getContext();
        Object[] args = jp.getArgs();
        if (args.length == 0) {
            throw new RuntimeException("分页方法的传入参数错误");
        }
        PageReqInfo pageReqInfo = null;
        for (Object arg : args) {
            if (arg instanceof PageReqInfo) {
                pageReqInfo = (PageReqInfo) arg;
                break;
            }
        }
        if (pageReqInfo == null) {
            throw new RuntimeException("分页方法的传入参数错误");
        }
        //计算limit和offset
        int offset = pageReqInfo.getPageNum() > 0 ? (pageReqInfo.getPageNum() - 1) * pageReqInfo.getPageSize() : 0;
        int limit = pageReqInfo.getPageSize() * (pageReqInfo.getPageNum() > 0 ? 1 : 0);
        context.setLimit(limit);
        context.setOffset(offset);
        Object obj;
        try {
            obj = jp.proceed();
            //填充total
            if (obj instanceof PageMessage) {
                PageMessage pageMessage = (PageMessage) obj;
                PageRespData pageRespData = pageMessage.getPageRespData();
                Collection<?> pageData = (Collection<?>) pageRespData.getPageData();
                pageRespData.setPageSize(pageData.size());
                pageRespData.setPageNum(pageReqInfo.getPageNum());
                pageRespData.setTotalCount(context.getTotalCount());
            } else {
                //支持用户自定义类型，需要手工取出相关数据进行封装
                log.info("参数类型是用户自定义，非标准类型!!!!!");
            }
        } finally {
            PageContext.removeContext();
        }
        return obj;
    }


}
