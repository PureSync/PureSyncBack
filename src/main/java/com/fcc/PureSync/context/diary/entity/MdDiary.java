package com.fcc.PureSync.context.diary.entity;

import com.fcc.PureSync.context.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name="tb_md_diary")
@Where(clause = "dy_status=true")
public class MdDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long dySeq;
    String dyDate;
    String dyTitle;
    String dyContents;
    Boolean dyStatus;

    @Builder.Default
    LocalDateTime dyWdate=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    Member member;

    @OneToOne
    @JoinColumn(name="emo_seq")
    Emotion emotion;
}
