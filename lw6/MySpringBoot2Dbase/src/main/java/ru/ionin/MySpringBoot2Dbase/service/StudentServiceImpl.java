package ru.ionin.MySpringBoot2Dbase.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ionin.MySpringBoot2Dbase.dao.StudentDAO;
import ru.ionin.MySpringBoot2Dbase.dao.StudentDAOImpl;
import ru.ionin.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        return studentDAO.createStudent(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    @Override
    @Transactional
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }
}
