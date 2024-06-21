package exam.athletebackend.discipline;
import exam.athletebackend.discipline.dtos.DisciplineResponseDTO;
import exam.athletebackend.result.ResultService;
import exam.athletebackend.result.ResultType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class DisciplineServiceTest {

    @InjectMocks
    DisciplineService disciplineService;

    @Mock
    DisciplineRepository disciplineRepository;

    @Mock
    ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDisciplinesReturnsAllDisciplines() {
        Discipline discipline1 = new Discipline();
        Discipline discipline2 = new Discipline();
        when(disciplineRepository.findAll()).thenReturn(Arrays.asList(discipline1, discipline2));

        disciplineService.getDisciplines();

        verify(disciplineRepository, times(1)).findAll();
    }

    @Test
    void addDisciplineSavesAndReturnsDiscipline() {
        DisciplineResponseDTO disciplineResponseDTO = new DisciplineResponseDTO(1L, "Name", ResultType.TIME, List.of(), List.of());
        Discipline discipline = new Discipline();
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);

        disciplineService.addDiscipline(disciplineResponseDTO);

        verify(disciplineRepository, times(1)).save(any(Discipline.class));
    }

    @Test
    void deleteDisciplineDeletesCorrectDiscipline() {
        disciplineService.deleteDiscipline(1L);

        verify(disciplineRepository, times(1)).deleteById(1L);
    }
}