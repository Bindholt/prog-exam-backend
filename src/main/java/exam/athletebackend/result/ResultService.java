package exam.athletebackend.result;

import exam.athletebackend.athlete.Athlete;
import exam.athletebackend.athlete.AthleteRepository;
import exam.athletebackend.discipline.Discipline;
import exam.athletebackend.discipline.DisciplineRepository;
import jakarta.transaction.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    private final AthleteRepository athleteRepository;
    private final DisciplineRepository disciplineRepository;

    public ResultService(ResultRepository resultRepository, AthleteRepository athleteRepository, DisciplineRepository disciplineRepository) {
        this.resultRepository = resultRepository;
        this.athleteRepository = athleteRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public ResultResponseDTO toDTO(Result result) {
        return new ResultResponseDTO(
                result.getId(),
                result.getAthlete().getName(),
                result.getAthlete().getClub(),
                result.getAthlete().getGender(),
                result.getAthlete().getBirthdate(),
                result.getDiscipline().getName(),
                result.getDate(),
                result.getResultType(),
                result.getResult()
        );
    }

    public Result fromDTO(ResultRequestDTO resultRequestDTO) {
        Athlete athlete = athleteRepository.findById(resultRequestDTO.athleteId()).orElse(null);
        Discipline discipline = disciplineRepository.findByName(resultRequestDTO.disciplineName()).orElse(null);
        ResultType resultType = null;
        if(discipline != null) {
            resultType = discipline.getResultType();
        }
        Result result = new Result();
        result.setAthlete(athlete);
        result.setDiscipline(discipline);
        result.setResultType(resultType);
        result.setDate(resultRequestDTO.date());
        result.setResult(resultRequestDTO.result());
        return result;
    }

    @Transactional
    public ResultResponseDTO addResult(ResultRequestDTO resultRequestDTO) {
        Result result = fromDTO(resultRequestDTO);

        Optional<Discipline> disciplineOpt = disciplineRepository.findByName(resultRequestDTO.disciplineName());

        if (disciplineOpt.isPresent()) {
            Discipline discipline = disciplineOpt.get();
            result.setDiscipline(discipline);
            result = resultRepository.save(result);

            discipline.getResults().add(result);
            disciplineRepository.save(discipline);
        } else {
            result = resultRepository.save(result);
        }

        return toDTO(result);
    }

    public Optional<ResultResponseDTO> updatePartialResult(Long id, ResultRequestDTO dto) {
        Map<String, Object> fields = dtoToMap(dto);
        Optional<Result> resultToUpdate = resultRepository.findById(id);
        resultToUpdate.ifPresent(result -> fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Result.class, key);
            if(field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, result, value);
            }
        }));
        return resultToUpdate.map(resultRepository::save).map(this::toDTO);
    }

    private Map<String, Object> dtoToMap(ResultRequestDTO dto) {
        Map<String, Object> fields = new HashMap<>();
        if(dto.date() != null) fields.put("date", dto.date());
        if(dto.result() != null) fields.put("result", dto.result());
        return fields;
    }

    public void deleteResult(Long id) {
        Optional<Result> result = resultRepository.findById(id);
        result.ifPresent(value -> {
            Athlete athlete = value.getAthlete();
            athlete.removeResult(value);
            athleteRepository.save(athlete);
            Discipline discipline = value.getDiscipline();
            discipline.removeResult(value);
            disciplineRepository.save(discipline);
            resultRepository.deleteById(id);
        });
    }
}
