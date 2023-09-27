package com.page.listener;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.page.PageContext;

import lombok.extern.slf4j.Slf4j;

/**
 * JOOQ的监听器，用来获取原始的SQL并添加分页限制
 *
 * @author zhangzhongguo <zhangzhongguo@kuaishou.com>
 * Created on 2023-07-04
 */
@Slf4j
public class PageQueryListener extends DefaultExecuteListener {

    /**
     * 拼接limit
     *
     * @param ctx The context containing information about the execution.
     */
    @Override
    public void prepareStart(ExecuteContext ctx) {
        PageContext context = PageContext.getContext();
        if (context.isPaginationDao()) {
            ExecuteType type = ctx.type();
            //查询条数
            if (type == ExecuteType.READ) {
                int startRow = context.getOffset();
                int pageSize = context.getLimit();
                String originalSql = ctx.sql();
                context.setOriginalSql(originalSql);
                context.setDslContext(ctx.dsl());
                //拼接分页数据
                String limitSql = originalSql + " limit " + startRow + "," + pageSize;
                ctx.sql(limitSql);
            }
        }
    }

    /**
     * 在bind中处理数据
     *
     * @param ctx The context containing information about the execution.
     */
    @Autowired
    public void bindEnd(ExecuteContext ctx) {
        PageContext context = PageContext.getContext();
        if (context.isPaginationDao()) {
            ExecuteType type = ctx.type();
            //绑定参数以后，可以获取到完整的SQL
            if (type == ExecuteType.READ) {
                String sql = ctx.query().getSQL(ParamType.INLINED);
                context.setOriginalSql(sql);
            }
        }

    }
}