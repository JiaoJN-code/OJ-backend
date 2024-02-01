package com.jjn.ojManagement.model.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.reflect.TypeToken;
import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.dto.Question.JudgeConfig;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 包装类VO转对象OBJ
     *
     * @param questionSubmitVo
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVo questionSubmitVo) {
        if (questionSubmitVo == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVo, questionSubmit);
        JudgeInfo judgeInfo = questionSubmitVo.getJudgeInfo();
        if (judgeInfo != null) {
            questionSubmit.setJudgeInfo(JSON.toJSONString(judgeInfo));
        }
        return questionSubmit;
    }

    /**
     * 对象OBJ转包装类VO
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVo objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVo questionSubmitVo = new QuestionSubmitVo();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVo);
        questionSubmitVo.setJudgeInfo(JSON.parseObject(questionSubmit.getJudgeInfo(), JudgeInfo.class));
        return questionSubmitVo;
    }

}
