package com.fcc.PureSync.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class SentimentResponseDto {
    private Document document;

    @Getter
    @Setter
    @ToString
    public static class Document {
        private String sentiment;
        private Confidence confidence;

    }

    @Getter
    @Setter
    @ToString
    public static class Confidence {
        private double negative;
        private double positive;
        private double neutral;
    }
}