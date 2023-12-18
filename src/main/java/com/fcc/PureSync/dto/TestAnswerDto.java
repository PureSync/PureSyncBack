package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.TestAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestAnswerDto {

    private Long ansSeq;
    private int testSeq;
    private String testAns;
    private int total;
    private Long memSeq;
    private LocalDateTime ansWdate;

    public static TestAnswerDto toDto(TestAnswer testAnswer) {
        return TestAnswerDto.builder()
                .ansSeq(testAnswer.getAnsSeq())
                .testSeq(testAnswer.getTestSeq())
                .testAns(testAnswer.getTestAns())
                .total(testAnswer.getTotal())
                .memSeq(testAnswer.getMember().getMemSeq())
                .ansWdate(testAnswer.getAnsWdate())
                .build();
    }
}
