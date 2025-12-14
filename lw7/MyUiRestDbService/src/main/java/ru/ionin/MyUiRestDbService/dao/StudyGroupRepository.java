package ru.ionin.MyUiRestDbService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ionin.MyUiRestDbService.entity.StudyGroup;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
}
