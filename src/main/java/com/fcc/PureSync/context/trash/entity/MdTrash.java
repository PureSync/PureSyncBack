package com.fcc.PureSync.context.trash.entity;

import com.fcc.PureSync.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name="tb_md_trash")
public class MdTrash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tsSeq;
    String tsContents;
    Boolean tsStatus;

    @Builder.Default
    LocalDateTime tsWdate=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mem_seq")
    Member member;
}
