package exam.athletebackend.discipline;

import exam.athletebackend.discipline.dtos.DisciplineRequestDTO;
import exam.athletebackend.discipline.dtos.DisciplineResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public List<DisciplineResponseDTO> getDisciplines() {
        return disciplineService.getDisciplines();
    }

    @PostMapping
    public DisciplineResponseDTO addDiscipline(@RequestBody DisciplineRequestDTO dto) {
        return disciplineService.addDiscipline(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
    }

}
