package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping(path = "students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(Model model){
        // List of students to show in the table
        model.addAttribute("students",studentService.getStudents());
        // Student object for new student to be added in the form
        Student student=new Student();
        model.addAttribute("stud",student);
        return "student";
    }

    // To be done --> add form validation to show exception
    @ResponseStatus
    @PostMapping
    public String registerNewStudent(@ModelAttribute("stud") Student student){
        try {
            studentService.addNewStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/students";
    }

    // This method for deleting students and is better done with @DeleteMapping.
    @GetMapping(path = "delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") String studentId){
        long stuId = Long.parseLong(studentId);
        System.out.println("Student Id here "+stuId);
        studentService.deleteStudent(stuId);
        return "redirect:/students";
    }

    // It has not been integrated into html yet, can be used with html request.
    @PutMapping(path = "update/{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }

}
