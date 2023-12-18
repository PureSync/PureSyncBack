package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.Board;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {
    String memNick;
    String memImg;

    public static MemberInfoDto toNickDto(MemberInfoDto dto) {
        return MemberInfoDto.builder()
                .memNick(dto.getMemNick())
                .build();
    }

    public static MemberInfoDto toProfileImgDto(MemberInfoDto dto) {
        return MemberInfoDto.builder()
                .memImg(dto.getMemImg())
                .build();
    }
}
