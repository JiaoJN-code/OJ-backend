package com.jjn.ojManagement.judge;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jjn.ojManagement.common.ErrorCode;
import com.jjn.ojManagement.exception.BusinessException;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandbox;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandboxFactory;
import com.jjn.ojManagement.judge.codeSandBox.CodeSandboxProxy;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jjn.ojManagement.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jjn.ojManagement.model.dto.Question.JudgeCase;
import com.jjn.ojManagement.model.entity.Question;
import com.jjn.ojManagement.model.entity.QuestionSubmit;
import com.jjn.ojManagement.model.enums.QuestionSubmitStatusEnum;
import com.jjn.ojManagement.model.vo.QuestionVo;
import com.jjn.ojManagement.service.QuestionService;
import nonapi.io.github.classgraph.json.JSONUtils;
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
public class JudgeServiceImpl implements JudgeService{

    @Value("${codeSandbox.type}")
    private String type;
    @Resource
    private QuestionService questionService;

    private final static Gson GSON = new Gson();

    @Override
    public ExecuteCodeResponse doJudge(QuestionSubmit questionSubmit) {
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
        List<JudgeCase> judgeCaseList = GSON.fromJson(question.getJudgeCase(), new TypeToken<List<JudgeCase>>() {
        }.getType());
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 返回执行信息
        return executeCodeResponse;
    }
}
