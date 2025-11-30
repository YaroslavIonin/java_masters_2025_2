package ru.ionin.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ionin.MySpringBoot2Dbase.entity.ApiResponse;
import ru.ionin.MySpringBoot2Dbase.entity.Discipline;
import ru.ionin.MySpringBoot2Dbase.service.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Discipline>>> getAllDisciplines() {
        try {
            List<Discipline> disciplines = disciplineService.getAllDisciplines();
            ApiResponse<List<Discipline>> response = new ApiResponse<>(
                    true,
                    "Disciplines retrieved successfully",
                    disciplines
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Discipline>> response = new ApiResponse<>(
                    false,
                    "Error: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Discipline>> getDiscipline(@PathVariable("id") int id) {
        try {
            Discipline discipline = disciplineService.getDiscipline(id);
            if (discipline != null) {
                ApiResponse<Discipline> response = new ApiResponse<>(
                        true,
                        "Discipline found",
                        discipline
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Discipline> response = new ApiResponse<>(
                        false,
                        "Discipline not found with id: " + id,
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Discipline> response = new ApiResponse<>(
                    false,
                    "Error: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Discipline>> saveDiscipline(@RequestBody Discipline discipline) {
        try {
            Discipline savedDiscipline = disciplineService.saveDiscipline(discipline);
            ApiResponse<Discipline> response = new ApiResponse<>(
                    true,
                    "Discipline saved successfully",
                    savedDiscipline
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Discipline> response = new ApiResponse<>(
                    false,
                    "Error saving discipline: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Discipline>> updateDiscipline(@RequestBody Discipline discipline) {
        try {
            Discipline updatedDiscipline = disciplineService.saveDiscipline(discipline);
            if (updatedDiscipline != null) {
                ApiResponse<Discipline> response = new ApiResponse<>(
                        true,
                        "Discipline updated successfully",
                        updatedDiscipline
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Discipline> response = new ApiResponse<>(
                        false,
                        "Discipline not found with id: " + discipline.getId(),
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Discipline> response = new ApiResponse<>(
                    false,
                    "Error updating discipline: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDiscipline(@PathVariable("id") int id) {
        try {
            disciplineService.deleteDiscipline(id);
            ApiResponse<String> response = new ApiResponse<>(
                    true,
                    "Discipline deleted successfully",
                    "Discipline with id " + id + " was deleted"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(
                    false,
                    "Error deleting discipline: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}