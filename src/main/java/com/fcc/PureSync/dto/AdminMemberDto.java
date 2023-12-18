package com.fcc.PureSync.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminMemberDto {
    private Long memSeq;
    private String memId;
    private String memNick;
    private String memEmail;
    private LocalDateTime memCreateAt;
    private String status;



    // Q-type을 만들기 위한 어노테이션
    @QueryProjection
    public AdminMemberDto(Long memSeq, String memId, String memNick, String memEmail, LocalDateTime memCreateAt, String status) {
        this.memSeq = memSeq;
        this.memId = memId;
        this.memNick = memNick;
        this.memEmail = memEmail;
        this.memCreateAt = memCreateAt;
        this.status = status;
    }
}
