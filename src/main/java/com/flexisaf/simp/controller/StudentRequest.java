package com.flexisaf.simp.controller;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StudentRequest {
	
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	private String otherNames;
	
	@NotBlank
	private String gender;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dob;
	
	@NotNull
	private int departmentId;
	
}
