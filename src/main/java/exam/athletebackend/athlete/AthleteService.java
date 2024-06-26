package exam.athletebackend.athlete;

import exam.athletebackend.athlete.dtos.AthleteRequestDTO;
import exam.athletebackend.athlete.dtos.AthleteResponseDTO;
import exam.athletebackend.discipline.DisciplineRepository;
import exam.athletebackend.discipline.DisciplineService;
import exam.athletebackend.result.ResultService;
import org.springframework.stereotype.Service;
import exam.athletebackend.result.Result;
import exam.athletebackend.discipline.Discipline;
import org.springframework.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AthleteService {
    private final AthleteRepository athleteRepository;
    private final ResultService resultService;
    private final DisciplineRepository disciplineRepository;

    public AthleteService(AthleteRepository athleteRepository, ResultService resultService, DisciplineRepository disciplineRepository) {
        this.athleteRepository = athleteRepository;
        this.resultService = resultService;
        this.disciplineRepository = disciplineRepository;
    }

    public AthleteResponseDTO toDTO(Athlete athlete) {
        return new AthleteResponseDTO(
                athlete.getId(),
                athlete.getName(),
                athlete.getBirthdate(),
                athlete.getGender(),
                athlete.getClub(),
                athlete.getResults().stream().map(resultService::toDTO).toList(),
                athlete.getDisciplines().stream().map(Discipline::getName).toList()
        );
    }

    public Athlete fromDTO(AthleteRequestDTO athleteResponseDTO) {
        return new Athlete(
                athleteResponseDTO.name(),
                athleteResponseDTO.birthdate(),
                athleteResponseDTO.gender(),
                athleteResponseDTO.club(),
                athleteResponseDTO.disciplines().stream()
                        .map(disciplineRepository::findByName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
    }

    public List<AthleteResponseDTO> getAthletes() {
        return athleteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<AthleteResponseDTO> getAthlete(Long id) {
        return athleteRepository.findById(id)
                .map(this::toDTO);
    }

    public AthleteResponseDTO addAthlete(AthleteRequestDTO dto) {
        return toDTO(athleteRepository.save(fromDTO(dto)));
    }

    public Optional<AthleteResponseDTO> updatePartialAthlete(Long id, AthleteRequestDTO dto) {
        Map<String, Object> fields = dtoToMap(dto);
        Optional<Athlete> athleteToUpdate = athleteRepository.findById(id);
        athleteToUpdate.ifPresent(
                athlete -> {
                    athlete.getDisciplines().clear();
                }
        );
        athleteToUpdate.ifPresent(athlete -> fields.forEach((key, value) -> {
            if ("disciplines".equals(key)) {
                List<String> disciplineNames = (List<String>) value;
                List<Discipline> disciplines = disciplineNames.stream()
                        .map(disciplineRepository::findByName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();
                disciplines.forEach(athlete::addDiscipline);
            } else {
                Field field = ReflectionUtils.findField(Athlete.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, athlete, value);
                }
            }
        }));
        return athleteToUpdate.map(athleteRepository::save).map(this::toDTO);
    }

    private Map<String, Object> dtoToMap(AthleteRequestDTO dto) {
        Map<String, Object> map = new HashMap<>();
        if (dto.name() != null) map.put("name", dto.name());
        if (dto.birthdate() != null) map.put("birthdate", dto.birthdate());
        if (dto.gender() != null) map.put("gender", dto.gender());
        if (dto.club() != null) map.put("club", dto.club());
        if (dto.results() != null) map.put("results", dto.results());
        if (dto.disciplines() != null) map.put("disciplines", dto.disciplines());
        return map;

    }

    public void deleteAthlete(Long id) {
        Optional<Athlete> athleteToDelete = athleteRepository.findById(id);
        if (athleteToDelete.isPresent()) {
            Athlete athlete = athleteToDelete.get();
            List<Result> results = new ArrayList<>(athlete.getResults());
            results.forEach(athlete::removeResult);
            List<Discipline> disciplines = new ArrayList<>(athlete.getDisciplines());
            disciplines.forEach(athlete::removeDiscipline);
            athleteRepository.deleteById(id);
        }
    }

    public List<AthleteResponseDTO> getAthleteByName(String name) {
        Optional<List<Athlete>> athletesOpt = athleteRepository.findAllByNameContains(name);
        return athletesOpt.map(athletes -> athletes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

}
