package com.jjn.codeSandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * @author 焦久宁
 * @date 2024/1/30
 */
public class DockerDemo {
    public static void main(String[] args) {
        // 获取默认的 docker client
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
    }
}