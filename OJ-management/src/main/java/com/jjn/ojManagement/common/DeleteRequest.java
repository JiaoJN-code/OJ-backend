package com.jjn.ojManagement.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author 焦久宁
 * @date 2024/1/7
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = -8398555501983171206L;
}
