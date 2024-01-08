package com.fcc.PureSync.context.member.dto;

import com.fcc.PureSync.context.member.entity.Body;
import com.fcc.PureSync.context.member.entity.Member;
import com.fcc.PureSync.core.config.UserRoleConfig;
import com.fcc.PureSync.entity.BaseEntity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignupDto extends BaseEntity {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    String memId;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    String memPassword;
    @NotBlank
    String memNick;
    @Email
    String memEmail;

    @NotBlank
    String memBirth;

    @Length(max = 1)
    String memGender;

    @DecimalMax(value = "300.00")
    @Positive
    Double bodyHeight;

    @DecimalMax(value = "500.00")
    @Positive
    Double bodyWeight;

    @DecimalMax(value = "500.00")
    @Positive
    Double bodyWishWeight;

    @Max(10000)
    @Positive
    Double bodyWishConscal;

    @Max(10000)
    @Positive
    Double bodyWishBurncal;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memId(this.memId)
                .memPassword(passwordEncoder.encode(this.memPassword))
                .memNick(this.memNick)
                .memEmail(this.memEmail)
                .memBirth(this.memBirth)
                .memGender(this.memGender)
                .memStatus(UserRoleConfig.UserRole.DISABLED.getLevel())
                .build();
    }


}