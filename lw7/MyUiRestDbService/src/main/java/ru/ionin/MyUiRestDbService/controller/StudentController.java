package ru.ionin.MyUiRestDbService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.ionin.MyUiRestDbService.dao.StudentRepository;
import ru.ionin.MyUiRestDbService.dao.StudyGroupRepository;
import ru.ionin.MyUiRestDbService.entity.Student;

import java.util.Optional;

@Slf4j
@RestController
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudyGroupRepository studyGroupRepository) {
        this.studentRepository = studentRepository;
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/list")
    public ModelAndView getAllStudents() {
        ModelAndView mav = new ModelAndView("list-students");
        mav.addObject("students", studentRepository.findAll());
        return mav;
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm() {
        ModelAndView mav = new ModelAndView("add-student-form");
        mav.addObject("student", new Student());
        mav.addObject("groups", studyGroupRepository.findAll());
        return mav;
    }

    @PostMapping("/saveStudent")
    public RedirectView saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return new RedirectView("list");
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId) {
        ModelAndView mav = new ModelAndView("add-student-form");

        Student student = studentRepository.findById(studentId)
                .orElse(new Student());

        mav.addObject("student", student);
        mav.addObject("groups", studyGroupRepository.findAll());

        return mav;
    }

    @GetMapping("/deleteStudent")
    public RedirectView deleteStudent(@RequestParam Long studentId, ModelAndView model) {
        studentRepository.deleteById(studentId);
        return new RedirectView("list");
    }
}
