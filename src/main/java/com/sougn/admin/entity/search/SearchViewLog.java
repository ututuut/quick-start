package com.sougn.admin.entity.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchViewLog {

	private String userLoginId;
	private String url;
	private String ip;
	private String params;
	private String logName;
	private String type;
	private Integer smallTtl;
	private Integer bigTtl;
	private String place;
	private String beginTime;
	private String endTime;
	
}
