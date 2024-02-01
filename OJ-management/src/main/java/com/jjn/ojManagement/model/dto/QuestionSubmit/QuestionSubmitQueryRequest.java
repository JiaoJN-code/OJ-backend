package com.jjn.ojManagement.model.dto.QuestionSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jjn.ojManagement.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;


    /**
     * 判题对象 JSON
     */
    private String judgeInfo;

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

    private static final long serialVersionUID = 1L;
}