package exam.athletebackend.athlete;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(AthleteController.class)
class AthleteControllerTest {

    @MockBean
    private AthleteService athleteService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Should return all athletes when getAthletes is called")
    void shouldReturnAllAthletesWhenGetAthletesIsCalled() throws Exception {
        AthleteDTO athleteOne = new AthleteDTO(1L, "Athlete One", LocalDate.of(1999,1, 1), "Mand", "Club One", null, null);
    AthleteDTO athleteTwo = new AthleteDTO(2L, "Athlete Two", LocalDate.of(1998, 1, 1), "Kvinde", "Club Two", null, null);

        when(athleteService.getAthletes()).thenReturn(Arrays.asList(athleteOne, athleteTwo));

        mockMvc.perform(get("/athletes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Athlete One")))
                .andExpect(jsonPath("$[0].birthdate", is("1999-01-01")));

    }

    @Test
    @DisplayName("Should return empty list when no athletes exist")
    void shouldReturnEmptyListWhenNoAthletesExist() throws Exception {
        when(athleteService.getAthletes()).thenReturn(List.of());

        mockMvc.perform(get("/athletes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}