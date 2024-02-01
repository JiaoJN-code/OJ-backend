package com.jjn.codeSandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jjn.codeSandbox.model.ExecuteCodeRequest;
import com.jjn.codeSandbox.model.ExecuteCodeResponse;
import com.jjn.codeSandbox.model.ExecuteMessage;
import com.jjn.codeSandbox.model.JudgeInfo;
import com.jjn.codeSandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 代码沙箱模板方法
 *
 * @author 焦久宁
 * @date 2024/1/30
 */
@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandbox {

    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";



    private static final Long TIME_OUT = 5000L;

    /**
     * 1. 把用户的代码保存为文件
     *
     * @param code
     * @return
     */
    public File saveCode(String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断目录是否存在，不存在则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        // 存放用户代码，隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        return FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);

    }

    /**
     * 2.编译代码 得到class文件
     *
     * @return 编译信息
     */
    public ExecuteMessage compileFile(File userCodeFile) {
        // 2.编译代码 得到class文件
        // 编译代码命令
        String compileCmd = String.format("javac -encoding UTF-8 %s", userCodeFile.getAbsoluteFile());
        Process process;
        try {
            process = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(process, "编译");
            System.out.println(executeMessage);
            return executeMessage;
        } catch (Exception e) {
//            return getErrorResponse(e);
            throw new RuntimeException("编译错误");
        }
    }

    /**
     * 3.执行代码 得到输出结果
     *
     * @param inputList 输入列表
     * @param codeFile  代码文件
     * @return 运行代码信息
     */
    public List<ExecuteMessage> runCodeFile(List<String> inputList, File codeFile) {
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        String userCodeParentPath = codeFile.getParentFile().getAbsolutePath();
        for (String inputArgs : inputList) {
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -Xmx256m -cp %s Main %s", userCodeParentPath, inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                // 守护线程，当程序执行时间超过限制时间，销毁runProcess
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        if (runProcess.isAlive()) {
                            runProcess.destroy();
                            log.error("程序超时");
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage runExecuteMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                runProcess.destroy();
                executeMessageList.add(runExecuteMessage);
            } catch (IOException e) {
//                return getErrorResponse(e);
                throw new RuntimeException("运行代码错误");
            }
        }
        return executeMessageList;
    }

    /**
     * 4.收集整理输出结果
     *
     * @param executeMessageList
     * @return
     */
    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTime = 0L;
        for (ExecuteMessage runExecuteMessage : executeMessageList) {
            String errorMessage = runExecuteMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                executeCodeResponse.setMessage(errorMessage);
                // 执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(runExecuteMessage.getMessage());
            maxTime = Math.max(maxTime, runExecuteMessage.getTime());
        }
        executeCodeResponse.setOutputList(outputList);
        // 正常运行完成
        if (outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        judgeInfo.setMemory(512L);

        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    /**
     * 5.文件清理 释放空间
     *
     * @param userCodeFile
     * @return
     */
    public boolean deleteFile(File userCodeFile) {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        if (userCodeFile.getParentFile() != null) {
            return FileUtil.del(userCodeParentPath);
        }
        return false;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();


        // 1.把用户的代码保存为文件
        File userCodeFile = saveCode(code);

        // 2.编译代码 得到class文件
        ExecuteMessage executeMessage = compileFile(userCodeFile);
        if (executeMessage.getExitValue() != 0) {
            log.error("编译代码错误：{}", executeMessage.getMessage());
        }

        // 3.执行代码 得到输出结果
        List<ExecuteMessage> executeMessageList = runCodeFile(inputList, userCodeFile);

        // 4.收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = getOutputResponse(executeMessageList);

        // 5.文件清理 释放空间
        boolean del = deleteFile(userCodeFile);
        log.info("删除" + (del ? "成功" : "失败"));

        // 6.错误处理 提升程序健壮性

        return executeCodeResponse;
    }

    /**
     * 获取错误响应
     *
     * @param e 错误信息
     * @return
     */
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setOutputList(null);
        executeCodeResponse.setMessage(e.getMessage());
        executeCodeResponse.setJudgeInfo(null);
        return executeCodeResponse;
    }
}
