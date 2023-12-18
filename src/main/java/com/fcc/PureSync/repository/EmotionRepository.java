package com.fcc.PureSync.repository;

import com.fcc.PureSync.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository <Emotion, Long> {
    Emotion findByEmoState(String emoState);
}
