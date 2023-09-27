package com.page;


import org.jooq.DSLContext;

import lombok.Data;

/**
 * 分页上下文数据
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Data
public class PageContext {

    private static final ThreadLocal<PageContext> CONTEXT = new ThreadLocal<>();

    /**
     * 标识是否在Controller层加注解
     */
    private boolean paginationController = false;

    /**
     * 标识是否在Dao层加注解
     */
    private boolean paginationDao = false;

    /**
     * 限制条数
     */
    private Integer limit = 10;

    /**
     * 起始行数
     */
    private Integer offset = 0;

    /**
     * 总条数
     */
    private Integer totalCount = 0;

    /**
     * JOOQ的查询引用
     */
    private DSLContext dslContext;

    /**
     * 原始的SQL
     */
    private String originalSql;

    /**
     * 获取PageContext
     */
    public static PageContext getContext() {
        PageContext ci = CONTEXT.get();
        if (ci == null) {
            ci = new PageContext();
            CONTEXT.set(ci);
        }
        return ci;
    }

    /**
     * 移除PageContext
     */
    public static void removeContext() {
        CONTEXT.remove();
    }

}
