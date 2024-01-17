package com.jjn.ojManagement.model.dto.QuestionSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 提交代码请求
 *
 * @author 焦久宁
 * @date 2024/1/15
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {
    private static final long serialVersionUID = -6584311545789815253L;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目id
     */
    private Long questionId;

}
