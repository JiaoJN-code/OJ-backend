package com.jjn.ojManagement.model.dto.Question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目更新清秀
 *
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class QuestionUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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
     * 题目答案
     */
    private String answer;

    /**
     * 判题配置(json数组)
     */
    private JudgeCase judgeCase;

    /**
     * 判题配置(json对象)
     */
    private JudgeConfig judgeConfig;

    /**
     * 创建用户id
     */
    private Long userid;

    private static final long serialVersionUID = 1L;
}
