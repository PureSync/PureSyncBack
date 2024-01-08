package com.fcc.PureSync.context.sleep.entity;

import com.fcc.PureSync.context.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="tb_sleep")
public class Sleep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sleepSeq;
    @Builder.Default
    private LocalDateTime sleepWdate = LocalDateTime.now();
    private LocalDateTime sleepGodate;
    private LocalDateTime sleepWudate;
    private Integer sleepColor;
    private Integer sleepCategory;
    private String sleepDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_seq")
    Member member;
}
