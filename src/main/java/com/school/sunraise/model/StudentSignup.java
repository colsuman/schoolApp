package com.school.sunraise.model;

import javax.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="student_signup")
public class StudentSignup {

   
	@Id
    private Long mobileNo;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String name;

    @Column(nullable=false)
    private String email;

    private String username;

    private String password;

    @Lob
    @Column(name="profile_pic")
    private MultipartFile profile_pic;

    @Transient
    private byte[] profilePicBytes;

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MultipartFile getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(MultipartFile profile_pic) {
		this.profile_pic = profile_pic;
	}

    public byte[] getProfilePicBytes() {
        return profilePicBytes;
    }

    public void setProfilePicBytes(byte[] profilePicBytes) {
        this.profilePicBytes = profilePicBytes;
    }

}