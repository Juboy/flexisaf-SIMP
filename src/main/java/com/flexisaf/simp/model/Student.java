package com.flexisaf.simp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name="students")
@NoArgsConstructor
@AllArgsConstructor
public class Student extends Audited {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "matric_number", unique = true, nullable = false)
	private String matricNumber;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = true)
	private String otherNames;
	
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 6)
	private Gender gender;
	
	@Column(nullable = false)
	private LocalDate dob;
	
	@JsonIgnore
	@ManyToOne
	private Department department;
}
