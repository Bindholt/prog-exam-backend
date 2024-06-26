package exam.athletebackend.athlete.dtos;

import exam.athletebackend.result.dtos.ResultResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record AthleteResponseDTO(Long id, String name, LocalDate birthdate, String gender, String club, List<ResultResponseDTO> results, List<String> disciplines) {
}
