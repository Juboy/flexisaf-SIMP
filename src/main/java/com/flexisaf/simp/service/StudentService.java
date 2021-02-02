package com.flexisaf.simp.service;

import java.time.LocalDate;
import java.util.List;

import com.flexisaf.simp.SimpException;
import com.flexisaf.simp.model.Student;

public interface StudentService {
	
	/**
	 * Save a new student record
	 * 
	 * @param email 		student email address
	 * @param firstName 	student first name
	 * @param lastName 		student last name
	 * @param otherNames 	student other name or NULL
	 * @param gender 
	 * @param dob
	 * @param departmentId
	 * @return Newly saved student entity
	 * @throws SimpException 
	 */
	public Student saveStudent(String email, String firstName, String lastName, String otherNames, String gender, LocalDate dob, int department) throws SimpException;
	
	/**
	 * Update an existing student's record
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param otherNames
	 * @param gender
	 * @param dob
	 * @param departmentId
	 * @return Updated student record
	 * @throws SimpException 
	 */
	public Student updateStudent(Long id, String firstName, String lastName, String otherNames, String gender, LocalDate dob,int departmentId) throws SimpException;
	
	/**
	 * Lists all saved student record
	 * 
	 * @return List of saved student's record
	 */
	public List<Student> findAllStudents();
	
	/**
	 * Get an existing student record by ID
	 * 
	 * @param id
	 * @return Single student record
	 * @throws SimpException 
	 */
	public Student findById(Long id) throws SimpException;
	
	/**
	 * Get an 
	 * 
	 * @param matricNumber
	 * @return A student who owns the matric number
	 * @throws SimpException
	 */
	public Student findByMatricNumber(String matricNumber) throws SimpException;
	
	/**
	 * Search for a student with search query. It filters the database by 
	 * First Name, Last Name, Other Name, Full Name(a combination of First Name, Last Name, and OtherNames), Gender(M or F), Department’s Name
	 * 
	 * @param query 	Search query string (could be empty)
	 * @param page		page for pagination
	 * @return List of students that meets the search criteria
	 */
	public List<Student> searchStudents(String query,int page);

	/**
	 * Search for a student with search query. It filters the database by 
	 * First Name, Last Name, Other Name, Full Name(a combination of First Name, Last Name, and OtherNames), Gender(M or F), Department’s Name, Created Date range
	 * 
	 * @param query 	Search query string (could be empty)
	 * @param from 		Created date range
	 * @param to		Created date range
	 * @param page		page for pagination
	 * @return List of students that meets the search criteria
	 */
	public List<Student> searchStudentsWithRange(String query, LocalDate from, LocalDate to, int page);
	
	public void sendBirthdayMails();
	
	/**
	 * Delete a student's record with its ID
	 * 
	 * @param studentId
	 * @throws SimpException 
	 */
	public void deleteStudent(Long studentId) throws SimpException;
}
