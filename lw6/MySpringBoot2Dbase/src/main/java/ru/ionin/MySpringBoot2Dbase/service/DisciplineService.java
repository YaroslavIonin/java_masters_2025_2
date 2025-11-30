package ru.ionin.MySpringBoot2Dbase.service;

import ru.ionin.MySpringBoot2Dbase.entity.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> getAllDisciplines();

    Discipline getDiscipline(int id);

    Discipline saveDiscipline(Discipline discipline);

    void deleteDiscipline(int id);
}