package com.fcc.PureSync.entity;

import com.fcc.PureSync.common.constant.EmailConstant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
@ToString
@Entity
@Table(name = "tb_member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memSeq;
    private Integer memStatus;
    private String memId;
    private String memPassword;
    private String memNick;
    private String memEmail;
    private String memBirth;
    private String memGender;
    private String memImg;
    private LocalDateTime memCreatedAt;
    @Builder.Default
    private LocalDateTime memLastLoginAt = LocalDateTime.now();

    public void updatePassword(String memPassword) {
        this.memPassword = memPassword;
    }
    public void updateStatus(int memStatus) {
        this.memStatus = memStatus;
    }

    public void enabledMemberLevel() {
        this.memStatus = EmailConstant.MEMBER_ENABLED_LEVEL;
    }

    public void updateNick(String memNick) { this.memNick = memNick;}
    public void updateProfileImg(String img) { this.memImg = img; }


}