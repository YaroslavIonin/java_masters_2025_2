package ru.ionin.MySpringBoot2Dbase.service;

import org.springframework.stereotype.Service;
import ru.ionin.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getAllStudents();

    Student createStudent(Student student);

    Student updateStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);
}
