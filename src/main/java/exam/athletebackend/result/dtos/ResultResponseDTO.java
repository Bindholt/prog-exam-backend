package exam.athletebackend.result.dtos;

import exam.athletebackend.result.ResultType;

import java.time.LocalDate;

public record ResultResponseDTO(
        Long id,
        String athleteName,
        String athleteClub,
        String athleteGender,
        LocalDate athleteBirthdate,
        String disciplineName,
        LocalDate date,
        ResultType resultType,
        String result
) { }
