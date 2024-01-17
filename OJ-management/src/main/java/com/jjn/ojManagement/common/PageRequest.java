package com.jjn.ojManagement.common;

import com.jjn.ojManagement.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class PageRequest {
    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序(默认升序)
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
