package com.jjn.ojManagement.model.dto.Question;

import com.jjn.ojManagement.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目查询类
 *
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {
    /**
     * 题目名称
     */
    private String title;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 创建用户id
     */
    private Long userid;

    private static final long serialVersionUID = 1L;
}
