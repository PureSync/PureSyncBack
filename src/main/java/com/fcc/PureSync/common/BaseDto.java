package com.fcc.PureSync.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDto {
	protected int pg;
	protected int start = 0;
	protected int rnum;
	protected String searchKey="";
	protected String SearchText="";

}
