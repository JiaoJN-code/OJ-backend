package com.jjn.ojManagement.model.dto.QuestionSubmit;

import com.jjn.ojManagement.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 焦久宁
 * @date 2024/2/1
 */
@Data
public class QuestionSubmitQueryByQuestionIdRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 2504923484413271170L;

    /**
     * 题目id
     */
    private Long questionId;
}
