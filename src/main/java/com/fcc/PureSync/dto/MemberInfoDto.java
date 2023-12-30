package com.fcc.PureSync.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcc.PureSync.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String memNick;
//    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String memImg;

    public static MemberInfoDto toDto(Member member) {
        return MemberInfoDto.builder()
                .memNick(member.getMemNick())
                .memImg(member.getMemImg())
                .build();
    }
}
