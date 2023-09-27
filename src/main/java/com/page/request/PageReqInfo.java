package com.page.request;


import lombok.Data;

/**
 * 分页信息
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Data
public class PageReqInfo {

    /**
     * 每页默认10条数据
     */
    private int pageSize = 10;

    /**
     * 页的起始行数
     */
    private int pageNum = 0;

}
