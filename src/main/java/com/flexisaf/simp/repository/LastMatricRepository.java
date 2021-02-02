package com.flexisaf.simp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flexisaf.simp.model.LastMatric;

@Repository
public interface LastMatricRepository extends JpaRepository<LastMatric, Long>{

	@Query(value = "Select last_id from lastID LIMIT 1", nativeQuery = true)
	Optional<String> findMaxMatricNo();
	
}
