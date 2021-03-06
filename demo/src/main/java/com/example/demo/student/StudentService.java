package com.example.demo.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()){
            throw new IllegalStateException("Add New Student: Email is already taken");
        }
        studentRepository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        // check if the student exists
        boolean exists = studentRepository.existsById(studentId);
        // if not exist, throw exception
        if (!exists) {
            throw new IllegalStateException("Student with id " + studentId +" does not exists!");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        // check if the student exists
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("Student with id \" + studentId +\" does not exists!"));
        // check name constrains
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        // check email constrains
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            // check email have been taken, if it is throw exception, otherwise, update
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("Update Student: Email already taken");
            }

            student.setEmail(email);
        }
    }

}
