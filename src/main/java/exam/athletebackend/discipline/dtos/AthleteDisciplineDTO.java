package exam.athletebackend.discipline.dtos;

import java.time.LocalDate;

public record AthleteDisciplineDTO(Long id, String name, String gender, LocalDate birthdate) {
}
