package com.fcc.PureSync.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name="tb_positive")
public class Positive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pvSeq;
    private String pvContents;
    private String pvTalker;
}
