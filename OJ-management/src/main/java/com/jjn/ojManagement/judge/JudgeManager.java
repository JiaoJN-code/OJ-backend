package com.jjn.ojManagement.judge;

import com.jjn.ojManagement.judge.strategy.DefaultJudgeStrategy;
import com.jjn.ojManagement.judge.strategy.JavaJudgeStrategy;
import com.jjn.ojManagement.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 *
 * @author 焦久宁
 * @date 2024/1/17
 */
@Service
public class JudgeManager {
    JudgeStrategy doJudge(String language) {
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy;
    }
}
