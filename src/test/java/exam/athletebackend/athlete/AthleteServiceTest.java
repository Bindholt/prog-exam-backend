package exam.athletebackend.athlete;
import exam.athletebackend.discipline.DisciplineRepository;
import exam.athletebackend.discipline.DisciplineService;
import exam.athletebackend.result.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AthleteServiceTest {

    @InjectMocks
    AthleteService athleteService;

    @Mock
    AthleteRepository athleteRepository;

    @Mock
    ResultService resultService;

    @Mock
    DisciplineService disciplineService;

    @Mock
    DisciplineRepository disciplineRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAthletesReturnsAllAthletes() {
        Athlete athlete1 = new Athlete("Name1", LocalDate.now(), "Gender1", "Club1", Arrays.asList());
        Athlete athlete2 = new Athlete("Name2", LocalDate.now(), "Gender2", "Club2", Arrays.asList());
        when(athleteRepository.findAll()).thenReturn(Arrays.asList(athlete1, athlete2));

        athleteService.getAthletes();

        verify(athleteRepository, times(1)).findAll();
    }

    @Test
    void getAthleteReturnsCorrectAthlete() {
        Athlete athlete = new Athlete("Name", LocalDate.now(), "Gender", "Club", Arrays.asList());
        when(athleteRepository.findById(anyLong())).thenReturn(Optional.of(athlete));

        athleteService.getAthlete(1L);

        verify(athleteRepository, times(1)).findById(1L);
    }

    @Test
    void addAthleteSavesAndReturnsAthlete() {
        AthleteDTO athleteDTO = new AthleteDTO(1L, "Name", LocalDate.now(), "Gender", "Club", Arrays.asList(), Arrays.asList());
        Athlete athlete = new Athlete("Name", LocalDate.now(), "Gender", "Club", Arrays.asList());
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athlete);

        athleteService.addAthlete(athleteDTO);

        verify(athleteRepository, times(1)).save(any(Athlete.class));
    }

    @Test
    void deleteAthleteDeletesCorrectAthlete() {
        Athlete athlete = new Athlete("Name", LocalDate.now(), "Gender", "Club", Arrays.asList());
        when(athleteRepository.findById(anyLong())).thenReturn(Optional.of(athlete));

        athleteService.deleteAthlete(1L);

        verify(athleteRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAthleteByNameReturnsCorrectAthletes() {
        Athlete athlete1 = new Athlete("Name1", LocalDate.now(), "Gender1", "Club1", Arrays.asList());
        Athlete athlete2 = new Athlete("Name2", LocalDate.now(), "Gender2", "Club2", Arrays.asList());
        when(athleteRepository.findAllByNameContains(anyString())).thenReturn(Optional.of(Arrays.asList(athlete1, athlete2)));

        athleteService.getAthleteByName("Name");

        verify(athleteRepository, times(1)).findAllByNameContains("Name");
    }
}
