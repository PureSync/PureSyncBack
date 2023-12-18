package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.BaseEntity;
import lombok.Getter;

@Getter
public class SignupDto extends BaseEntity {

    String memId;
    String memPassword;
    String memNick;
    String memEmail;
    String memBirth;
    String memGender;
    //
    Double bodyHeight;
    Double bodyWeight;
    Double bodyWishWeight;
    Double bodyWishConscal;

}