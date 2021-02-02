package com.flexisaf.simp.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flexisaf.simp.SimpException;
import com.flexisaf.simp.model.Response;
import com.flexisaf.simp.model.Student;
import com.flexisaf.simp.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;
	
	@Scheduled(cron = "0 0 7 * * ?",zone = "Africa/Lagos")
	public void ScheduleBirthdayTask() {
		//Attempt to send birthday mails at 7:00 am everyday
		studentService.sendBirthdayMails();
	}
	
	@PostMapping
	public ResponseEntity<Response> saveStudent(@Valid @RequestBody StudentRequest request) throws SimpException{
		
		if(!request.getGender().matches("M|F")) throw new SimpException("Gender must be M or F");
		
		Student student = studentService.saveStudent(request.getEmail(),request.getFirstName(), request.getLastName(), request.getOtherNames(), request.getGender(), request.getDob(), request.getDepartmentId());
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student saved successfully")
				.data(student)
				.build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{studentId}")
	public ResponseEntity<Response> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentRequest request) throws SimpException{
		
		if(!request.getGender().matches("M|F")) throw new SimpException("Gender must be M or F");
		
		Student student = studentService.updateStudent(studentId, request.getFirstName(), request.getLastName(), request.getOtherNames(), request.getGender(), request.getDob(), request.getDepartmentId());
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student record updated successfully")
				.data(student)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping
	public ResponseEntity<Response> getAllStudents(){
		
		List<Student> students = studentService.findAllStudents();
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Students fetched successfully")
				.data(students)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<Response> getStudentById(@PathVariable Long studentId) throws SimpException{
		
		Student student = studentService.findById(studentId);
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student fetched successfully")
				.data(student)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/matric/{matricNo}")
	public ResponseEntity<Response> getStudentByMatric(@PathVariable String matricNo) throws SimpException{
		
		Student student = studentService.findByMatricNumber(matricNo);
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student fetched successfully")
				.data(student)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Response> search(@RequestParam(name = "query") String query, 
			@RequestParam(name = "page") @NotNull @Positive int page) throws SimpException{
		if(page<1) throw new SimpException("Page should start from 1");
		
		List<Student> students= studentService.searchStudents(query, page);
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student fetched successfully")
				.data(students)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/search-range")
	public ResponseEntity<Response> searchRange(@RequestParam(name = "query") String query, @DateTimeFormat(iso = ISO.DATE) LocalDate from, @DateTimeFormat(iso = ISO.DATE) LocalDate to, int page) throws SimpException{
		if(page<1) throw new SimpException("Page should start from 1");
		List<Student> students= studentService.searchStudentsWithRange(query, from, to, page);
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student fetched successfully")
				.data(students)
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseEntity<Response> deleteStudent(@PathVariable Long studentId) throws SimpException{
		studentService.deleteStudent(studentId);
		
		Response response = Response.builder()
				.status(Response.SUCCESS)
				.message("Student record successfully deleted")
				.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
}
