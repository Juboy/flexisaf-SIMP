package com.flexisaf.simp.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flexisaf.simp.SimpException;
import com.flexisaf.simp.model.Department;
import com.flexisaf.simp.model.Student;
import com.flexisaf.simp.repository.DepartmentRepository;
import com.flexisaf.simp.repository.LastMatricRepository;
import com.flexisaf.simp.repository.StudentRepository;
import com.flexisaf.simp.serviceImpl.StudentServiceImpl;


@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test.properties")
public class StudentServiceTest {

	@Mock private StudentRepository studentRepository;
	@Mock private DepartmentRepository departmentRepository;
	@Mock private LastMatricRepository lastMatricRepository;
	@Mock private JavaMailSender javaMailSender;
	
	private StudentService studentService;
	private Department department;
	private Student student;
	
	@BeforeEach
	public void setUp() {
		
		department = new Department();
		department.setId((long)1);
		department.setName("Maths");
		
		student= Student.builder()
				.email("mt@mt.com")
				.dob(LocalDate.of(1995, 06, 12))
				.firstName("John")
				.lastName("Doe")
				.otherNames(null)
				.gender("M")
				.department(department)
				.build();
		
		Mockito.when(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(department));

		studentService = new StudentServiceImpl(studentRepository, departmentRepository, lastMatricRepository, javaMailSender);


	}
	
	@Test
	public void shouldSaveStudentTest() throws SimpException {
		
		Mockito.when(lastMatricRepository.findMaxMatricNo()).thenReturn(Optional.of("FLEXISAF/001"));
		
		studentService.saveStudent("mt@mt.com", "John", "Doe", null, "M", LocalDate.of(1995, 06, 12), 1);
		
		Mockito.verify(studentRepository).save(Mockito.any(Student.class));
		
		//Invalid department
		Mockito.when(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Assertions.assertThrows(SimpException.class, () -> studentService.saveStudent("mt@mt.com", "John", "Doe", null, "M", LocalDate.of(1995, 06, 12), 1));
		
	}
	
	@Test
	public void shouldFailToSaveStudentTest_InvalidDob() throws SimpException {
		
		Mockito.when(lastMatricRepository.findMaxMatricNo()).thenReturn(Optional.of("FLEXISAF/001"));
		
		Assertions.assertThrows(SimpException.class, () -> studentService.saveStudent("mt@mt.com", "John", "Doe", null, "M", LocalDate.of(1985, 06, 12), 1));
		
	}
	
	@Test
	public void shouldUpdateStudent() throws SimpException{
		Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		Mockito.when(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(department));

		studentService.updateStudent((long)1, "John", "Doe", null, "M", LocalDate.of(1995, 06, 12), 1);
		
		Mockito.verify(studentRepository).save(Mockito.any(Student.class));
		
	}
	
	@Test
	public void shouldFailToUpdateStudent_InvalidDepartment() throws SimpException{
		Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		Mockito.when(departmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Assertions.assertThrows(SimpException.class, () -> studentService.updateStudent((long)1, "John", "Doe", null, "M", LocalDate.of(1995, 06, 12), 1));
		
	}
	
	@Test
	public void shouldFetchAllStudents(){
		Mockito.when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
		
		Assertions.assertTrue(studentService.findAllStudents().size()==1);
	}
	
	@Test
	public void shouldFindById() throws SimpException {
		Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		
		Assertions.assertTrue(studentService.findById((long)1).getEmail()!=null);
		
		//invalid Id
		Mockito.when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Assertions.assertThrows(SimpException.class, () -> studentService.findById((long)1));
		
	}
	
	@Test
	public void shouldFindByMatric() throws SimpException {
		Mockito.when(studentRepository.findByMatricNumber(Mockito.anyString())).thenReturn(Optional.of(student));
		
		Assertions.assertTrue(studentService.findByMatricNumber("FLEXISAF/001").getEmail()!=null);
	}
	
	@Test
	public void shouldSearchStudents() throws SimpException {
		Mockito.when(studentRepository.searchStudents(Mockito.anyString(), Mockito.anyInt())).thenReturn(Arrays.asList(student));
		Mockito.when(studentRepository.searchStudentsWithDateRange(Mockito.anyString(), any(LocalDate.class), any(LocalDate.class), Mockito.anyInt())).thenReturn(Arrays.asList(student));
		
		Assertions.assertTrue(studentService.searchStudents("mt", 0).size()==1);
		Assertions.assertTrue(studentService.searchStudentsWithRange("mt", LocalDate.now(), LocalDate.now(), 0).size()==1);
	}
	
	@Test
	public void shouldSendBirthdayMails() {
		Mockito.when(studentRepository.findBirthday(Mockito.any(LocalDate.class))).thenReturn(Arrays.asList(student));
		
		studentService.sendBirthdayMails();
		
		Mockito.verify(javaMailSender, times(1)).send(Mockito.any(SimpleMailMessage.class));
	
	}

	
}
