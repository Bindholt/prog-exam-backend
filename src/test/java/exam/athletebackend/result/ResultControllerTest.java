package exam.athletebackend.result;
import com.fasterxml.jackson.databind.ObjectMapper;
import exam.athletebackend.result.dtos.ResultRequestDTO;
import exam.athletebackend.result.dtos.ResultResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ResultController.class)
class ResultControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ResultController(resultService)).build();
    }

    @Test
    void deleteResultTest() throws Exception {
        doNothing().when(resultService).deleteResult(any(Long.class));

        mockMvc.perform(delete("/results/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(resultService, times(1)).deleteResult(1L);
    }

    @Test
    void addResultTest() throws Exception {
        ResultRequestDTO resultRequest = new ResultRequestDTO(1L, "Discipline", LocalDate.now(), "Result");

        ResultResponseDTO savedResult = new ResultResponseDTO(1L, "Athlete Name", "Athlete Club", "Athlete Gender", LocalDate.of(1990, 1, 1), "Discipline", LocalDate.now(), ResultType.POINTS, "Result");

        when(resultService.addResult(any(ResultRequestDTO.class))).thenReturn(savedResult);

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(resultRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedResult.id().intValue())))
                .andExpect(jsonPath("$.athleteName", is(savedResult.athleteName())))
                .andExpect(jsonPath("$.athleteClub", is(savedResult.athleteClub())))
                .andExpect(jsonPath("$.athleteGender", is(savedResult.athleteGender())))
                .andExpect(jsonPath("$.athleteBirthdate", is(savedResult.athleteBirthdate().toString())))
                .andExpect(jsonPath("$.disciplineName", is(savedResult.disciplineName())))
                .andExpect(jsonPath("$.date", is(savedResult.date().toString())))
                .andExpect(jsonPath("$.resultType", is(savedResult.resultType().toString())))
                .andExpect(jsonPath("$.result", is(savedResult.result())));
    }

}