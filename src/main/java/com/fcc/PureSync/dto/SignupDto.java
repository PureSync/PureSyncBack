package com.fcc.PureSync.dto;

import com.fcc.PureSync.entity.BaseEntity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignupDto extends BaseEntity {

    @Pattern(regexp =  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    String memId;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    String memPassword;
    @NotBlank
    String memNick;
    @Email
    String memEmail;
//    @Past 삭제!!!!!!!!!!!!!!
    String memBirth;
    @Length(max=1)
     String memGender;
    //
    @DecimalMax(value = "300.00")
    @Positive
    Double bodyHeight;
    @DecimalMax(value = "500.00")
    @Positive
    Double bodyWeight;
    @Max(10000)
    @Positive
    Double bodyWishWeight;
    @Max(10000)
    @Positive
    Double bodyWishConscal;

}