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
        AthleteDTO athlete1 = new AthleteDTO(1L, "Athlete One", 25, "Male", "Club One", null, null);
        AthleteDTO athlete2 = new AthleteDTO(2L, "Athlete Two", 30, "Female", "Club Two", null, null);
        List<AthleteDTO> athletes = Arrays.asList(athlete1, athlete2);

        when(athleteService.getAthletes()).thenReturn(athletes);

        mockMvc.perform(get("/athletes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Athlete One")))
                .andExpect(jsonPath("$[0].age", is(25)))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].club", is("Club One")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Athlete Two")))
                .andExpect(jsonPath("$[1].age", is(30)))
                .andExpect(jsonPath("$[1].gender", is("Female")))
                .andExpect(jsonPath("$[1].club", is("Club Two")));
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

    @Test
    @DisplayName("Should add athlete when addAthlete is called with valid data")
    void shouldAddAthleteWhenAddAthleteIsCalledWithValidData() throws Exception {
        AthleteDTO athlete = new AthleteDTO(null, "Athlete One", 25, "Male", "Club One", null, null);
        AthleteDTO savedAthlete = new AthleteDTO(1L, "Athlete One", 25, "Male", "Club One", null, null);

        when(athleteService.addAthlete(athlete)).thenReturn(savedAthlete);

        mockMvc.perform(post("/athletes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Athlete One")))
                .andExpect(jsonPath("$.age", is(25)))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.club", is("Club One")));
    }

    @Test
    @DisplayName("Should update athlete when updatePartialAthlete is called with valid data")
    void shouldUpdateAthleteWhenUpdatePartialAthleteIsCalledWithValidData() throws Exception {
        AthleteDTO athlete = new AthleteDTO(1L, "Athlete One", 25, "Male", "Club One", null, null);
        AthleteDTO updatedAthlete = new AthleteDTO(1L, "Athlete One Updated", 26, "Male", "Club One", null, null);

        when(athleteService.updatePartialAthlete(1L, athlete)).thenReturn(Optional.of(updatedAthlete));

        mockMvc.perform(patch("/athletes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Athlete One Updated")))
                .andExpect(jsonPath("$.age", is(26)))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.club", is("Club One")));
    }

    @Test
    @DisplayName("Should return 404 when updatePartialAthlete is called with invalid id")
    void shouldReturn404WhenUpdatePartialAthleteIsCalledWithInvalidId() throws Exception {
        AthleteDTO athlete = new AthleteDTO(1L, "Athlete One", 25, "Male", "Club One", null, null);

        when(athleteService.updatePartialAthlete(1L, athlete)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/athletes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isNotFound());
    }
}