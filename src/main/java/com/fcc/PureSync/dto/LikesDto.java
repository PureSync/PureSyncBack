package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.Likes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {

    private Long likesSeq;
    private Long memSeq;
    private Long boardSeq;

    public static LikesDto toDto(Likes likes) {
        return LikesDto.builder()
                .likesSeq(likes.getLikesSeq())
                .memSeq(likes.getMember().getMemSeq())
                .boardSeq(likes.getBoard().getBoardSeq())
                .build();
    }
}
