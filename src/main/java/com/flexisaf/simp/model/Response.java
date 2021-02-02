package com.flexisaf.simp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class Response {

	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	
	private int status;
	private String message;
	
	private Object data;
}
