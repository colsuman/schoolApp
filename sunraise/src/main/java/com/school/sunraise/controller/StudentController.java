package com.school.sunraise.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.sunraise.model.Student;
import com.school.sunraise.model.StudentForgotPwd;
import com.school.sunraise.model.StudentSignup;
import com.school.sunraise.model.UserType;
import com.school.sunraise.service.StudentService;


@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @RequestMapping("/getOrdermilestone")
    public String showOrderMilestoneForm() {
        return "ordermilestoneByOrderId";
    }

    @PostMapping("/login")
    public String loginStudent(@ModelAttribute StudentSignup studentSignup, Model model) {

        StudentSignup user = studentService.loginStudentData(studentSignup);

        if (user != null) {

            if (user.getUserType() == UserType.DIRECTOR) {
                return "directorHome";
            } 
            else if (user.getUserType() == UserType.TEACHER) {
                return "teacherHome";
            } 
            else if (user.getUserType() == UserType.STUDENT) {
                return "studentHome";
            }

        }

        model.addAttribute("message", "Invalid Username or Password");
        return "login";
    }
    
    @PostMapping("/forgotPwd")
    public String updatePassword(@ModelAttribute StudentForgotPwd studentForgotPwd, Model model) {

        int result = studentService.updatePassword(studentForgotPwd);

        if(result > 0){
            model.addAttribute("message","Password updated successfully");
        }else{
            model.addAttribute("message","User not found");
        }

        return "login";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute StudentSignup signup,
            @RequestParam("profilePicFile") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {

        try {
        	signup.setProfile_pic(file);
        	System.out.println("email : "+ signup.getEmail());
        	System.out.println("Image : "+ signup.getProfile_pic());
        	studentService.registerUser(signup);

            redirectAttributes.addFlashAttribute("message",
                    "Account created successfully. Please login.");
        } catch (Exception e) {
        	e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Signup failed. Please try again.");
        }

        return "redirect:/login";
    } 

    @GetMapping("/student/image/{imageType}/{mobileNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable UserType imageType,
            @PathVariable Long mobileNo) {

    	System.out.println("Image");
		byte[] image = studentService.getStudentImage(imageType, mobileNo);
		
		if (image == null || image.length == 0) {
		return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok()
		.contentType(MediaType.IMAGE_JPEG) // change if PNG
		.body(image);
	}
    
 /*   @PostMapping("/signup")
    public String registerStudent(@ModelAttribute StudentSignup studentSignup,
            @RequestParam("profilePic") MultipartFile file,
                                  RedirectAttributes redirectAttributes) throws Exception {

        try {
            //studentService.postStudentData(studentSignup);

         if(!file.isEmpty()) {

             String uploadDir = "uploads/";

             File dir = new File(uploadDir);
             if(!dir.exists()) {
                 dir.mkdirs();
             }

             String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

             File saveFile = new File(uploadDir + fileName);
             file.transferTo(saveFile);

             studentSignup.setProfilePic(fileName);
         }

         studentService.postStudentData(studentSignup);

        // return "redirect:/login";

        	
            redirectAttributes.addFlashAttribute("message",
                    "Account created successfully. Please login.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("message",
                    "Signup failed. Please try again.");
        }

        return "redirect:/login";
    } 
    
   /* @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/saveSignupDetails")
    public String getMilestoneData(StudentSignup studentSignup, Model model) {

       // List<StudentSignup> milestones =
                studentService.postStudentData(studentSignup);

        LOGGER.info("Fetched milestone data for order id {} : {}", studentSignup.getName());

        //model.addAttribute("mileStoneAndRollbacks", milestones);

        return "mileStoneDetails";
    }
    */
    
    @GetMapping
    public List<Student> getStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/deleteStudent/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        int result = studentService.deleteStudent(id);

        if (result > 0) {
            return ResponseEntity.ok("Student deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Student not found");
        }
    }
}