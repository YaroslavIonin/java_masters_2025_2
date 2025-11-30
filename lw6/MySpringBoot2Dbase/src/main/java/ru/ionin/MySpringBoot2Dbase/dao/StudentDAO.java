package ru.ionin.MySpringBoot2Dbase.dao;
import org.springframework.stereotype.Repository;
import ru.ionin.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Repository
public interface StudentDAO {

    List<Student> getAllStudents();

    Student createStudent(Student student);

    Student updateStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);
}