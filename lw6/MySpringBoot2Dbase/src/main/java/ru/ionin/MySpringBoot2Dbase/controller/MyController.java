package ru.ionin.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ionin.MySpringBoot2Dbase.entity.ApiResponse;
import ru.ionin.MySpringBoot2Dbase.entity.Student;
import ru.ionin.MySpringBoot2Dbase.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            ApiResponse<List<Student>> response = new ApiResponse<>(
                    true,
                    "Students retrieved successfully",
                    students
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Student>> response = new ApiResponse<>(
                    false,
                    "Error: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudent(@PathVariable("id") int id) {
        try {
            Student student = studentService.getStudent(id);
            if (student != null) {
                ApiResponse<Student> response = new ApiResponse<>(
                        true,
                        "Student found",
                        student
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Student> response = new ApiResponse<>(
                        false,
                        "Student not found with id: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Student> response = new ApiResponse<>(
                    false,
                    "Error: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            ApiResponse<Student> response = new ApiResponse<>(
                    true,
                    "Student created successfully",
                    createdStudent
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Student> response = new ApiResponse<>(
                    false,
                    "Error creating student: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/students")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(student);
            if (updatedStudent != null) {
                ApiResponse<Student> response = new ApiResponse<>(
                        true,
                        "Student updated successfully",
                        updatedStudent
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Student> response = new ApiResponse<>(
                        false,
                        "Student not found with id: " + student.getId(),
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Student> response = new ApiResponse<>(
                    false,
                    "Error updating student: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable("id") int id) {
        try {
            studentService.deleteStudent(id);
            ApiResponse<String> response = new ApiResponse<>(
                    true,
                    "Student deleted successfully",
                    "Student with id " + id + " was deleted"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(
                    false,
                    "Error deleting student: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}