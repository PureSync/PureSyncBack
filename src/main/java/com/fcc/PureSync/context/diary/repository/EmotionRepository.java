package com.fcc.PureSync.context.diary.repository;

import com.fcc.PureSync.context.diary.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository <Emotion, Long> {
    Emotion findByEmoState(String emoState);
}
