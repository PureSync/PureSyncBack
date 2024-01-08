package com.fcc.PureSync.context.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MdDiaryRequestDto {
    String dyDate;
    String dyTitle;
    String dyContents;
    String emoState;
}
