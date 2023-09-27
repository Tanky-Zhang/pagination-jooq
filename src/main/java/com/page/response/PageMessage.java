package com.page.response;

import lombok.Data;

/**
 * Controller的页面返回协议
 *
 * @author zhangzhongguo <zhngzhongguo@gmail.com>
 * Created on 2023-09-27
 */
@Data
public class PageMessage<T> {

    /**
     * resultCode
     */
    private int result;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 分页数据
     */
    private PageRespData<T> pageRespData;
}
