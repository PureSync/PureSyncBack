package com.fcc.PureSync.context.trash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MdTrashResponseDto {
    Long tsSeq;
    String tsContents;
}
