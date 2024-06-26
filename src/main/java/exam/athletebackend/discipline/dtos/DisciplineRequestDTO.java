package exam.athletebackend.discipline.dtos;

import exam.athletebackend.result.ResultType;

public record DisciplineRequestDTO(String name, ResultType resultType) {
}
