package exam.athletebackend.result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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