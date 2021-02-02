package com.flexisaf.simp.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.flexisaf.simp.SimpException;
import com.flexisaf.simp.model.Department;
import com.flexisaf.simp.model.Gender;
import com.flexisaf.simp.model.LastMatric;
import com.flexisaf.simp.model.Student;
import com.flexisaf.simp.repository.DepartmentRepository;
import com.flexisaf.simp.repository.LastMatricRepository;
import com.flexisaf.simp.repository.StudentRepository;
import com.flexisaf.simp.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{
	
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;
	private final LastMatricRepository lastMatricRepository;
	private final JavaMailSender javaMailSender;

	@Override
	public Student saveStudent(String email, String firstName, String lastName, String otherNames, String gender, LocalDate dob, int departmentId) throws SimpException {
		
		verifyDob(dob);
		
		Optional<Department> department = departmentRepository.findById((long) departmentId);
		if(!department.isPresent()) throw new SimpException("Invalid department");
				
		String matricNumber = this.getNextID();
		
		Student student = Student.builder()
				.email(email)
				.matricNumber(matricNumber)
				.firstName(firstName)
				.lastName(lastName)
				.otherNames(otherNames)
				.gender(Gender.valueOf(gender))
				.dob(dob)
				.department(department.get())
				.build();
		
		lastMatricRepository.save(LastMatric.builder().id((long)1).lastId(matricNumber).build());
		
		return studentRepository.save(student);
		
	}
	
	@Override
	public Student updateStudent(Long id, String firstName, String lastName, String otherNames, String gender,
			LocalDate dob, int departmentId) throws SimpException {
		
		Optional<Student> student = studentRepository.findById(id);
		if(!student.isPresent()) throw new SimpException("Cannot find student");
		
		Optional<Department> department = departmentRepository.findById((long) departmentId);
		if(!department.isPresent()) throw new SimpException("Invalid department");
		
		verifyDob(dob);
		
		student.get().setFirstName(firstName);
		student.get().setLastName(lastName);
		student.get().setOtherNames(otherNames);
		student.get().setGender(Gender.valueOf(gender));
		student.get().setDob(dob);
		student.get().setDepartment(department.get());
		return studentRepository.save(student.get());
	}

	@Override
	public List<Student> findAllStudents() {
		
		return studentRepository.findAll();
	}

	@Override
	public Student findById(Long id) throws SimpException {
		
		Optional<Student> student = studentRepository.findById(id);
		
		if(!student.isPresent()) throw new SimpException("Cannot find student");
		return student.get();
	}
	
	@Override
	public Student findByMatricNumber(String matricNumber) throws SimpException {
		
		Optional<Student> student = studentRepository.findByMatricNumber(matricNumber);
		
		if(!student.isPresent()) throw new SimpException("Cannot find student");
		return student.get();
	}
	
	@Override
	public List<Student> searchStudents(String query, int page) {
		int offset = (page-1)*10;
		List<Student> students = studentRepository.searchStudents(query,offset);
		
		return students;
	}
	
	@Override
	public List<Student> searchStudentsWithRange(String query, LocalDate from, LocalDate to, int page) {
		
		int offset = (page-1)*10;
		List<Student> students = studentRepository.searchStudentsWithDateRange(query, from, to, offset);
		
		return students;
	}
	
	@Override
	public void sendBirthdayMails() {
		List<Student> students = studentRepository.findBirthday(LocalDate.now());
		
		for (Student student : students) {
			try {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(student.getEmail());
				message.setSubject("Happy Birthday from FLEXISAF");
				message.setText("Dear sir/madam,"+System.lineSeparator() +
						"Flexisaf is wishing you a happy birthday. Wish you a big "+ ChronoUnit.YEARS.between(student.getDob(), LocalDate.now()) + "."+
						System.lineSeparator() + System.lineSeparator() + 
						"Best regards," +System.lineSeparator() +
						"Damilola Ogedengbe"+System.lineSeparator() +
						"People Operations"+System.lineSeparator() +
						"Flexisaf");
				javaMailSender.send(message);

			} catch (MailException exception) {
				
			}
		}
		
	}
	
	
	private String getNextID() {
		Optional<String> id = lastMatricRepository.findMaxMatricNo();
		
		if(!id.isPresent()) return "FLEXISAF/001";
		
		String fullMatric = id.get();
		
		int number = Integer.parseInt(fullMatric.split("/")[1]);
		
		return String.format("FLEXISAF/%03d", number + 1);
		
	}
	
	private boolean verifyDob(LocalDate dob) throws SimpException {
		
		long age = ChronoUnit.YEARS.between(dob, LocalDate.now());
		
		if(age < 18 || age > 25) throw new SimpException("Date of birth should be between 18 and 25");
		
		return true;
	}
	

}
