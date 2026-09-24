package com.school.sunraise.dao;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.school.sunraise.model.Student;
import com.school.sunraise.model.StudentForgotPwd;
import com.school.sunraise.model.StudentSignup;
import com.school.sunraise.model.UserType;


@Repository
public class StudentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getOrderMilestonekData(String orderId) {
        String query = "select * from milestone_and_rollback where \"ORDER_ID\" = ?";
        try {
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Student.class), orderId);
        } catch (Exception e) {
            LOGGER.error("Error fetching milestone data for orderId: {}", orderId, e);
            return null;
        }
    }
    public int postStudentDetails(StudentSignup signup) throws IOException {
        byte[] imageBytes = signup.getProfile_pic().getBytes();
        LOGGER.info("Uploading file: {}, size: {}", signup.getProfile_pic().getOriginalFilename(), signup.getProfile_pic().getSize());

        String query = "INSERT INTO student_signup (mobile_no, name, email, username, password, user_type, profile_pic) VALUES (?, ?, ?, ?, ?, ?, lo_from_bytea(0, ?))";

        return jdbcTemplate.update(
                query,
                signup.getMobileNo(),
                signup.getName(),
                signup.getEmail(),
                signup.getUsername(),
                signup.getPassword(),
                signup.getUserType().name(),
                imageBytes
        );
    }
	 
    public StudentSignup loginStudentData(StudentSignup studentSignup) {
        String query = "SELECT mobile_no, name, email, username, password, user_type FROM student_signup WHERE username = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                StudentSignup s = new StudentSignup();
                s.setMobileNo(rs.getLong("mobile_no"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setUserType(UserType.valueOf(rs.getString("user_type")));
                return s;
            }, studentSignup.getUsername(), studentSignup.getPassword());
        } catch (Exception e) {
            LOGGER.warn("Login failed for username: {}", studentSignup.getUsername());
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
            LOGGER.info("Rows updated: {}", rows);
            return rows;
        } catch (Exception e) {
            LOGGER.error("Error updating password for email: {}", studentForgotPwd.getEmailId(), e);
            return 0;
        }
    }
	 
	 
    public byte[] getStudentImage(UserType imageType, Long mobileNo) {
        String sql = "SELECT lo_get(profile_pic) AS profile_pic FROM student_signup WHERE user_type = ? AND mobile_no = ?";
        return jdbcTemplate.query(sql,
                ps -> {
                    ps.setString(1, imageType.name());
                    ps.setLong(2, mobileNo);
                },
                rs -> {
                    if (rs.next()) {
                        return rs.getBytes("profile_pic");
                    }
                    return null;
                });
    }

    public StudentSignup getDirectorDetails() {
        String sql = "SELECT mobile_no, name, email, username, user_type, lo_get(profile_pic) AS profile_pic FROM student_signup WHERE user_type = 'DIRECTOR' LIMIT 1";
        try {
            return jdbcTemplate.query(sql, rs -> {
                if (rs.next()) {
                    StudentSignup s = new StudentSignup();
                    s.setMobileNo(rs.getLong("mobile_no"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setUsername(rs.getString("username"));
                    s.setUserType(UserType.valueOf(rs.getString("user_type")));
                    s.setProfilePicBytes(rs.getBytes("profile_pic"));
                    return s;
                }
                return null;
            });
        } catch (Exception e) {
            LOGGER.error("Error fetching director details", e);
            return null;
        }
    }

    public List<StudentSignup> getAllTeachers() {
        String sql = "SELECT mobile_no, name, email, username, user_type, lo_get(profile_pic) AS profile_pic FROM student_signup WHERE user_type = 'TEACHER'";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                StudentSignup s = new StudentSignup();
                s.setMobileNo(rs.getLong("mobile_no"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setUserType(UserType.valueOf(rs.getString("user_type")));
                s.setProfilePicBytes(rs.getBytes("profile_pic"));
                return s;
            });
        } catch (Exception e) {
            LOGGER.error("Error fetching teachers", e);
            return null;
        }
    }

    public int deleteStudent(Long mobileNo) {
        String sql = "DELETE FROM student_signup WHERE mobile_no = ?";
        return jdbcTemplate.update(sql, mobileNo);
    }

}
