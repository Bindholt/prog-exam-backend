package exam.athletebackend.result.dtos;

import java.time.LocalDate;

public record ResultRequestDTO(
        Long athleteId,
        String disciplineName,
        LocalDate date,
        String result
) { }
