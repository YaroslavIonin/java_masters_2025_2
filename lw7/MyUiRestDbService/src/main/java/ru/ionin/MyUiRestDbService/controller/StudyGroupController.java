package ru.ionin.MyUiRestDbService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.ionin.MyUiRestDbService.entity.StudyGroup;
import ru.ionin.MyUiRestDbService.dao.StudyGroupRepository;

import java.util.Optional;

@Slf4j
@Controller
public class StudyGroupController {

    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupController(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/groups")
    public ModelAndView getAllGroups() {
        ModelAndView mav = new ModelAndView("list-groups");
        mav.addObject("groups", studyGroupRepository.findAll());
        return mav;
    }

    @GetMapping("/addGroupForm")
    public ModelAndView addGroupForm() {
        ModelAndView mav = new ModelAndView("add-group-form");
        mav.addObject("group", new StudyGroup());
        return mav;
    }

    @PostMapping("/saveGroup")
    public RedirectView saveGroup(@ModelAttribute StudyGroup group) {
        studyGroupRepository.save(group);
        return new RedirectView("groups");
    }

    @GetMapping("/showGroupUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long groupId) {
        ModelAndView mav = new ModelAndView("add-group-form");
        Optional<StudyGroup> optionalGroup = studyGroupRepository.findById(groupId);
        mav.addObject("group", optionalGroup.orElse(new StudyGroup()));
        return mav;
    }

    @GetMapping("/deleteGroup")
    public RedirectView deleteGroup(@RequestParam Long groupId) {
        studyGroupRepository.deleteById(groupId);
        return new RedirectView("groups");
    }
}
