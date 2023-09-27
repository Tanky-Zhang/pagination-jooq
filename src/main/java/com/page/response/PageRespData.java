package com.page.response;

import lombok.Data;

/**
 * 页面返回数据
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-08-16
 */
@Data
public class PageRespData<T> {

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页面条数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int totalCount;

    /**
     * 页面数据
     */
    private T pageData;

}
