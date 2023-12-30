package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.TestQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionDto {

    private Long queSeq;
    private Long queNum;
    private String queContents;
    private Long testSeq;

    public static TestQuestionDto toDto(TestQuestion testQuestion) {
        return TestQuestionDto.builder()
                .queSeq(testQuestion.getQueSeq())
                .queNum(testQuestion.getQueNum())
                .queContents(testQuestion.getQueContents())
                .testSeq(testQuestion.getTestSeq())
                .build();
    }
}
