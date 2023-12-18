package com.fcc.PureSync.dto;


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
public class MemberDetailDto {
    private Long memSeq;
    private String memId;
    private String memNick;
    private String memEmail;
    private String status;
    private String memBirth;
    private String memGender;
    private String memImg;
    private LocalDateTime memCreateAt;
    private LocalDateTime memLastLoginAt;

    private Double bodyHeight;
    private Double bodyWeight;
    private Double bodyWishWeight;
    private Double bodyWishConsCal;
    private Double bodyWishBurnCal;
    private LocalDateTime bodyWdate;

    @QueryProjection

    public MemberDetailDto(Long memSeq, String memId, String memNick, String memEmail, String status, String memBirth, String memGender, String memImg, LocalDateTime memCreateAt, LocalDateTime memLastLoginAt, Double bodyHeight, Double bodyWeight, Double bodyWishWeight, Double bodyWishConsCal, Double bodyWishBurnCal, LocalDateTime bodyWdate) {
        this.memSeq = memSeq;
        this.memId = memId;
        this.memNick = memNick;
        this.memEmail = memEmail;
        this.status = status;
        this.memBirth = memBirth;
        this.memGender = memGender;
        this.memImg = memImg;
        this.memCreateAt = memCreateAt;
        this.memLastLoginAt = memLastLoginAt;
        this.bodyHeight = bodyHeight;
        this.bodyWeight = bodyWeight;
        this.bodyWishWeight = bodyWishWeight;
        this.bodyWishConsCal = bodyWishConsCal;
        this.bodyWishBurnCal = bodyWishBurnCal;
        this.bodyWdate = bodyWdate;
    }
}
