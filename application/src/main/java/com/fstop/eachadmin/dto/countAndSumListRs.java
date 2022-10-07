package com.fstop.eachadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class countAndSumListRs {
	private String TOTALCOUNT;
	private String OKCOUNT;
	private String FAILCOUNT;
	private String PENDCOUNT;
}
