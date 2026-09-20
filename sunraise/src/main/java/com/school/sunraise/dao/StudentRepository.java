package com.school.sunraise.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.school.sunraise.model.Student;
import com.school.sunraise.model.StudentForgotPwd;
import com.school.sunraise.model.StudentSignup;
import com.school.sunraise.model.UserType;


@Repository
public class StudentRepository  {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 public List<Student> getOrderMilestonekData(String orderId) {
			//String query = "select * from milestone_and_rollback where \"ORDER_ID\" = ? order by \"CREATION_TIME\" asc";
			String query = "select * from milestone_and_rollback where \"ORDER_ID\" = ?";
			try {
				return jdbcTemplate.query(query,BeanPropertyRowMapper.newInstance(Student.class),new Object[] {orderId});

			} catch (IncorrectResultSizeDataAccessException e) {
				return null;
			}
}
	 public int postStudentDetails(StudentSignup signup) throws IOException {
		 
		 byte[] imageBytes = null; 
		 imageBytes = signup.getProfile_pic().getBytes();
		 
		 System.out.println(signup.getProfile_pic().getOriginalFilename());
		 System.out.println(signup.getProfile_pic().getSize());

		    String query = "INSERT INTO student_signup (mobile_no, name, email, username, password, user_type, profile_pic) VALUES (?, ?, ?, ?, ?, ?, ?)";

		    return jdbcTemplate.update(
		            query,
		            signup.getMobileNo(),
		            signup.getName(),
		            signup.getEmail(),
		            signup.getUsername(),
		            signup.getPassword(),
		            signup.getUserType().name(),   // convert enum to String
		            imageBytes
		    );
		}
	 
	 public StudentSignup loginStudentData(StudentSignup studentSignup) {

		    String query = "SELECT * FROM student_signup WHERE username = ? AND password = ?";

		    try {

		        return jdbcTemplate.queryForObject(
		                query,
		                new BeanPropertyRowMapper<>(StudentSignup.class),
		                studentSignup.getUsername(),
		                studentSignup.getPassword()
		        );

		    } catch (Exception e) {
		        return null;
		    }
		}


	 public int updatePassword(StudentForgotPwd studentForgotPwd) {

		    String query = "UPDATE student_signup SET password = ? WHERE email = ? AND user_type = ?";

		    try {

		        int rows = jdbcTemplate.update(
		                query,
		                studentForgotPwd.getPassword(),
		                studentForgotPwd.getEmailId(),
		                studentForgotPwd.getUserType()
		        );

		        System.out.println("Rows updated: " + rows);

		        return rows;

		    } catch (Exception e) {
		        e.printStackTrace();
		        return 0;
		    }
		}
	 
	 
	 @SuppressWarnings("deprecation")
	public byte[] getStudentImage(UserType imageType, Long mobileNo) {

		    String sql = "SELECT profile_pic FROM student_signup WHERE user_type = ? AND mobile_no = ?";

		    return jdbcTemplate.query(sql,
		            new Object[]{imageType.name(), mobileNo},
		            rs -> {
		                if (rs.next()) {
		                    return rs.getBytes("profile_pic");
		                }
		                return null;
		            });
		}
	 
	 public int deleteStudent(Long id) {
		    String sql = "DELETE FROM student_signup WHERE id = ?";
		    
		    return jdbcTemplate.update(sql, id);
		}
	
	 }
