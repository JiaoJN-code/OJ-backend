package com.jjn.ojManagement.model.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.dto.Question.JudgeConfig;
import com.jjn.ojManagement.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 焦久宁
 * @date 2024/1/6
 */
@Data
public class QuestionVo implements Serializable {
    private final static Gson GSON = new Gson();
    private static final long serialVersionUID = 1L;
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
     * 提交数
     */
    private Integer submitNum;
    /**
     * 题目通过数
     */
    private Integer acceptedNum;
    /**
     * 判题配置(json数组)
     */
    private List<JudgeCase> judgeCase;
    /**
     * 判题配置(json对象)
     */
    private JudgeConfig judgeConfig;
    /**
     * 点赞数
     */
    private Integer thumbNum;
    /**
     * 收藏数
     */
    private Integer favourNum;
    /**
     * 创建用户id
     */
    private Long userid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否已点赞
     */
    private Boolean hasThumb;
    /**
     * 是否已收藏
     */
    private Boolean hasFavour;

    /**
     * 包装类VO转对象OBJ
     *
     * @param questionVo
     * @return
     */
    public static Question voToObj(QuestionVo questionVo) {
        if (questionVo == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVo, question);
        List<String> tagList = questionVo.getTags();
        if (tagList != null) {
            question.setTags(GSON.toJson(tagList));
        }
        List<JudgeCase> judgeCase = questionVo.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = questionVo.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        return question;
    }

    /**
     * 对象OBJ转包装类VO
     *
     * @param question
     * @return
     */
    public static QuestionVo objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        questionVo.setTags(GSON.fromJson(question.getTags(), new TypeToken<List<String>>() {
        }.getType()));
        questionVo.setJudgeCase(JSON.parseObject(question.getJudgeCase(), new TypeReference<List<JudgeCase>>() {
        }));
        questionVo.setJudgeConfig(GSON.fromJson(question.getJudgeConfig(), new TypeToken<JudgeConfig>() {
        }.getType()));
        return questionVo;
    }
}
