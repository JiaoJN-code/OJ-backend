package com.jjn.ojManagement.judge;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandboxFactory;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandboxProxy;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jjn.ojManagement.judge.strategy.JudgeContext;
import com.jjn.ojManagement.judge.strategy.JudgeStrategy;
import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.enums.QuestionSubmitStatusEnum;
import com.jjn.ojManagement.model.vo.JudgeInfo;
import com.jjn.ojManagement.service.QuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 判题实现类
 *
 * @author 焦久宁
 * @date 2024/1/16
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    private final static Gson GSON = new Gson();
    @Value("${codeSandbox.type}")
    private String type;
    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public JudgeInfo doJudge(QuestionSubmit questionSubmit) {
        // 1. 获取题目信息
        Question question = questionService.getById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 2. 如果题目提交状态部位等待中，就不重复执行了
        if (!Objects.equals(questionSubmit.getStatus(), QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        // 3. 更改判题的状态为运行中
        // TODO 是否经过数据库操作
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        // 4. 调用沙箱，获取到执行信息
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<JudgeCase> judgeCaseList = JSON.parseArray(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 5.进行判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        // 根据编程语言选择判题策略
        JudgeStrategy judgeStrategy = judgeManager.doJudge(questionSubmit.getLanguage());
        // 返回执行信息
        return judgeStrategy.doJudge(judgeContext);
    }
}
