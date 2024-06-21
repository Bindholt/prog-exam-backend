package exam.athletebackend.result;
import exam.athletebackend.athlete.AthleteDTO;
import exam.athletebackend.discipline.DisciplineDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResultControllerTest {

    @InjectMocks
    private ResultController resultController;

    @Mock
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteResultReturnsNoContentStatus() {
        doNothing().when(resultService).deleteResult(anyLong());

        ResponseEntity<Void> response = resultController.deleteResult(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(resultService, times(1)).deleteResult(anyLong());
    }
}