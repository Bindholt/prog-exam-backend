package exam.athletebackend.discipline;

import exam.athletebackend.athlete.Athlete;
import exam.athletebackend.discipline.dtos.AthleteDisciplineDTO;
import exam.athletebackend.discipline.dtos.DisciplineResponseDTO;
import exam.athletebackend.result.ResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    private final ResultService resultService;

    public DisciplineService(DisciplineRepository disciplineRepository, ResultService resultService) {
        this.disciplineRepository = disciplineRepository;
        this.resultService = resultService;
    }

    public AthleteDisciplineDTO toSimpleDTO(Athlete athlete) {
        return new AthleteDisciplineDTO(
                athlete.getId(),
                athlete.getName(),
                athlete.getGender(),
                athlete.getBirthdate()
        );
    }

    public DisciplineResponseDTO toDTO(Discipline discipline) {
        return new DisciplineResponseDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType(),
                discipline.getAthletes().stream().map(this::toSimpleDTO).toList(),
                discipline.getResults().stream().map(resultService::toDTO).toList()
        );
    }

    public List<DisciplineResponseDTO> getDisciplines() {
        return disciplineRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DisciplineResponseDTO addDiscipline(DisciplineResponseDTO disciplineResponseDTO) {
        Discipline discipline = new Discipline();
        discipline.setName(disciplineResponseDTO.name());
        discipline.setResultType(disciplineResponseDTO.resultType());
        disciplineRepository.save(discipline);
        return toDTO(discipline);
    }

    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }
}
