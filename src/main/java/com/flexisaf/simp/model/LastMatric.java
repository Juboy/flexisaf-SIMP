package com.flexisaf.simp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="lastID")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastMatric {
	
	@Id
	public Long id;
	
	public String lastId;
}
