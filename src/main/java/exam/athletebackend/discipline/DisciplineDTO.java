package exam.athletebackend.discipline;

import exam.athletebackend.result.ResultResponseDTO;
import exam.athletebackend.result.ResultType;

import java.util.List;

public record DisciplineDTO(Long id, String name, ResultType resultType, List<AthleteDisciplineDTO> athletes, List<ResultResponseDTO> results) {
}
