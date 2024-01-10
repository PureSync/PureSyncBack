package com.fcc.PureSync.context.member.entity;

import com.fcc.PureSync.core.constant.EmailConstant;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mp_mem_role")
public class MpMemRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long memRoleSeq;
    private Long memSeq;
    private Integer roleSeq;



    public void enabledMemberLevel() {
        this.roleSeq = EmailConstant.MEMBER_ENABLED_LEVEL;
    }
}
