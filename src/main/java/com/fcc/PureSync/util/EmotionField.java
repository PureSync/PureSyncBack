package com.fcc.PureSync.util;

public enum EmotionField {
    좋음(1),
    행복(1),
    상쾌(1),
    사랑(1),
    감사(1),
    만족(1),
    평범(2),
    무난(2),
    안정(2),
    슬픔(3),
    분노(3),
    불안(3),
    걱정(3),
    외로움(3),
    우울(3);




    private final Integer value;

    EmotionField(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }



}