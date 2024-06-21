package exam.athletebackend.discipline.dtos;

import exam.athletebackend.result.dtos.ResultResponseDTO;
import exam.athletebackend.result.ResultType;

import java.util.List;

public record DisciplineResponseDTO(Long id, String name, ResultType resultType, List<AthleteDisciplineDTO> athletes, List<ResultResponseDTO> results) {
}
