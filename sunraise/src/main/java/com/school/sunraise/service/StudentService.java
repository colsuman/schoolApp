package com.school.sunraise.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.sunraise.dao.StudentRepository;
import com.school.sunraise.model.Student;
import com.school.sunraise.model.StudentForgotPwd;
import com.school.sunraise.model.StudentSignup;
import com.school.sunraise.model.UserType;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	public List<Student> getOrderMilestonekData(String orderId) {
		return studentRepository.getOrderMilestonekData(orderId);
	}
	
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteStudent(Long id) {
		// TODO Auto-generated method stub
		return studentRepository.deleteStudent(id);
	}

	public int postStudentData(StudentSignup signup) throws IOException {
		// TODO Auto-generated method stub
		return studentRepository.postStudentDetails(signup);
	}

	public int registerUser(StudentSignup signup) throws IOException {
	return studentRepository.postStudentDetails(signup);
	}

	public int updatePassword(StudentForgotPwd studentForgotPwd) {
		// TODO Auto-generated method stub
		return studentRepository.updatePassword(studentForgotPwd);
	}

	public StudentSignup loginStudentData(StudentSignup studentSignup) {
		// TODO Auto-generated method stub
		return studentRepository.loginStudentData(studentSignup);
	}

	public byte[] getStudentImage(UserType imageType, Long mobileNo) {
		
		return studentRepository.getStudentImage(imageType,mobileNo);
	}


}
