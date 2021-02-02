package com.flexisaf.simp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flexisaf.simp.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	Optional<Student> findByMatricNumber(String matricNumber);
	
	
	@Query(value="SELECT * FROM students "
			+ " WHERE DAYOFMONTH(students.dob) = DAYOFMONTH(?1) "
			+ " AND MONTH(students.dob) = MONTH(?1)", nativeQuery = true)
	List<Student> findBirthday(LocalDate now);
	
	
	@Query(value="SELECT * "
			+ " from students left join departments "
			+ " on students.department_id = departments.id "
			+ " where (students.first_name LIKE %?1% "
			+ " or students.last_name LIKE %?1% "
			+ " or students.other_names LIKE %?1% "
			+ " or CONCAT(students.first_name, ' ', students.last_name, ' ', students.other_names) LIKE %?1% ) "
			+ " or students.gender = ?1 "
			+ " or departments.name LIKE %?1%  "
			+ " and students.created_date between ?2 and ?3 "
			+ " limit 10 offset ?4", nativeQuery = true)
	List<Student> searchStudentsWithDateRange(String query, LocalDate from, LocalDate to, int offset);
	
	
	@Query(value="SELECT * "
			+ " from students left join departments "
			+ " on students.department_id = departments.id "
			+ " where ( students.first_name LIKE %?1% "
			+ " or students.last_name LIKE %?1% "
			+ " or students.other_names LIKE %?1% "
			+ " or CONCAT(students.first_name, ' ', students.last_name, ' ', students.other_names) LIKE %?1% )"
			+ " or students.gender LIKE %?1% "
			+ " or departments.name LIKE %?1%  "
			+ " limit 10 offset ?2", nativeQuery = true)
	List<Student> searchStudents(String query, int offset);
}
