package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.Body;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoUpdateDto {

    Double bodyHeight;
    Double bodyWeight;
    Double bodyWishWeight;
    Double bodyWishConscal;
    Double bodyWishBurncal;

    public static MemberInfoUpdateDto entityToDto(Body body) {
        return MemberInfoUpdateDto.builder()
                .bodyHeight(body.getBodyHeight())
                .bodyWeight(body.getBodyWeight())
                .bodyWishWeight(body.getBodyWishWeight())
                .bodyWishConscal(body.getBodyWishConscal())
                .bodyWishBurncal(body.getBodyWishBurncal())
                .build();
    }

}
