package com.flexisaf.simp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.flexisaf.simp.model.Department;
import com.flexisaf.simp.model.Student;

import lombok.RequiredArgsConstructor;

@DataJpaTest
@TestPropertySource("classpath:test.properties")
@RequiredArgsConstructor
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	@Test
	public void shouldRetrieveBirthday() {
		Department department = new Department();
		department.setName("Maths");
		
		departmentRepository.save(department);
		
		Student student= Student.builder()
				.email("alex@flexisaf.com")
				.dob(LocalDate.of(1995, 06, 12))
				.matricNumber("FLEXISAF/001")
				.firstName("Alexa")
				.lastName("Bliss")
				.otherNames(null)
				.gender("M")
				.department(department)
				.build();
		
		Student student2= Student.builder()
				.email("john.doe@flexisaf.com")
				.matricNumber("FLEXISAF/002")
				.dob(LocalDate.of(1999, 07, 11))
				.firstName("John")
				.lastName("Doe")
				.otherNames(null)
				.gender("F")
				.department(department)
				.build();
		
		studentRepository.save(student);
		studentRepository.save(student2);
		List<Student> students = studentRepository.findBirthday(LocalDate.of(2021, 06, 12));
		
		assertEquals(1, students.size());
	}
	
	@Test
	public void shouldSearch() {
		Department department = new Department();
		department.setName("Maths");
		
		departmentRepository.save(department);
		
		Student student= Student.builder()
				.email("alex@flexisaf.com")
				.dob(LocalDate.of(1995, 06, 12))
				.matricNumber("FLEXISAF/001")
				.firstName("Alexa")
				.lastName("Bliss")
				.otherNames(null)
				.gender("M")
				.department(department)
				.build();
		
		Student student2= Student.builder()
				.email("john.doe@flexisaf.com")
				.matricNumber("FLEXISAF/002")
				.dob(LocalDate.of(1999, 07, 11))
				.firstName("John")
				.lastName("Doe")
				.otherNames(null)
				.gender("F")
				.department(department)
				.build();
		
		studentRepository.save(student);
		studentRepository.save(student2);
		
		List<Student> students = studentRepository.searchStudents("John", 0);
		List<Student> students1 = studentRepository.searchStudents("Alex", 0);
		List<Student> students2 = studentRepository.searchStudents("John Doe", 0);
		List<Student> students3 = studentRepository.searchStudents("Maths", 0);
		
		assertEquals(1, students.size());
		assertEquals(1, students1.size());
		assertEquals(1, students2.size());
		assertEquals(2, students3.size());
	}
	
}
