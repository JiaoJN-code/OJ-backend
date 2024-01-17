package com.jjn.ojManagement.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交vo对象
 *
 * @author 焦久宁
 * @date 2024/1/8
 */
@Data
public class QuestionSubmitVo implements Serializable {
    private static final long serialVersionUID = 2553771621307297115L;
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题对象
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 回答用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date creatTime;

}
