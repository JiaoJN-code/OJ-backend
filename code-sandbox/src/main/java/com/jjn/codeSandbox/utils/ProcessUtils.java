package com.jjn.codeSandbox.utils;

import com.jjn.codeSandbox.model.ExecuteMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 进程工具类
 *
 * @author 焦久宁
 * @date 2024/1/25
 */
@Slf4j
public class ProcessUtils {
    /**
     * 执行进程并获取信息
     *
     * @param process
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process process, String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = process.waitFor();
            executeMessage.setExitValue(exitValue);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 获取编译输出结果
            if (exitValue == 0) {
                log.info(opName + "成功");
                StringBuilder compileOutputBuilder = new StringBuilder();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputBuilder.toString());
            } else {
                log.error(opName + "失败，错误码：" + exitValue);
                StringBuilder compileOutputBuilder = new StringBuilder();
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputBuilder.toString());
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorCompileOutputBuilder = new StringBuilder();
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorCompileOutputBuilder.append(errorCompileOutputLine);
                }
                executeMessage.setErrorMessage(errorCompileOutputBuilder.toString());
                return null;
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return executeMessage;

    }
}
