package com.fcc.PureSync.entity;

import com.fcc.PureSync.common.constant.EmailConstant;
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
