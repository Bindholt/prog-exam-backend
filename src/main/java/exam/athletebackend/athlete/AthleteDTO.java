package exam.athletebackend.athlete;

import exam.athletebackend.result.dtos.ResultResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record AthleteDTO(Long id, String name, LocalDate birthdate, String gender, String club, List<ResultResponseDTO> results, List<String> disciplines) {
}
