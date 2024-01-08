package com.fcc.PureSync.context.diary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name="tb_emotion")
public class Emotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long emoSeq;
    String emoState;
    Integer emoField;
}
