package exam.athletebackend.result;

import java.time.LocalDate;

public record ResultRequestDTO(
        Long athleteId,
        String disciplineName,
        LocalDate date,
        String result
) { }
