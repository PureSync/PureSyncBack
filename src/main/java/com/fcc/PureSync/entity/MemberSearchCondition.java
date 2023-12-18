package com.fcc.PureSync.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSearchCondition {
    private String category;
    private String keyword;
    private String status;
}
