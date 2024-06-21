package exam.athletebackend.athlete;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
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
    void getAthletesTest() throws Exception {
        AthleteDTO athlete1 = new AthleteDTO(1L, "John Doe", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());
        AthleteDTO athlete2 = new AthleteDTO(2L, "Jane Doe", LocalDate.of(1992, 2, 2), "Female", "Club", new ArrayList<>(), new ArrayList<>());
        List<AthleteDTO> athletes = Arrays.asList(athlete1, athlete2);

        when(athleteService.getAthletes()).thenReturn(athletes);

        mockMvc.perform(get("/athletes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].birthdate", is("1990-01-01")))
                .andExpect(jsonPath("$[0].gender", is("Male")))
                .andExpect(jsonPath("$[0].club", is("Club")))
                .andExpect(jsonPath("$[0].results", is(empty())))
                .andExpect(jsonPath("$[0].disciplines", is(empty())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")))
                .andExpect(jsonPath("$[1].birthdate", is("1992-02-02")))
                .andExpect(jsonPath("$[1].gender", is("Female")))
                .andExpect(jsonPath("$[1].club", is("Club")))
                .andExpect(jsonPath("$[1].results", is(empty())))
                .andExpect(jsonPath("$[1].disciplines", is(empty())));
    }

    @Test
    void addAthleteTest() throws Exception {
        AthleteDTO athlete = new AthleteDTO(null, "John Doe", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());
        AthleteDTO savedAthlete = new AthleteDTO(1L, "John Doe", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());

        when(athleteService.addAthlete(org.mockito.ArgumentMatchers.any(AthleteDTO.class))).thenReturn(savedAthlete);

        mockMvc.perform(post("/athletes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.birthdate", is("1990-01-01")))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.club", is("Club")))
                .andExpect(jsonPath("$.results", is(empty())))
                .andExpect(jsonPath("$.disciplines", is(empty())));
    }

    @Test
    void updateAthleteTest() throws Exception {
        AthleteDTO athlete = new AthleteDTO(1L, "John Doe", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());
        AthleteDTO updatedAthlete = new AthleteDTO(1L, "John Smith", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());

        when(athleteService.updatePartialAthlete(org.mockito.ArgumentMatchers.any(Long.class), org.mockito.ArgumentMatchers.any(AthleteDTO.class))).thenReturn(Optional.of(updatedAthlete));

        mockMvc.perform(patch("/athletes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Smith")))
                .andExpect(jsonPath("$.birthdate", is("1990-01-01")))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.club", is("Club")))
                .andExpect(jsonPath("$.results", is(empty())))
                .andExpect(jsonPath("$.disciplines", is(empty())));
    }

    @Test
    void deleteAthleteTest() throws Exception {
        AthleteDTO athlete = new AthleteDTO(1L, "John Doe", LocalDate.of(1990, 1, 1), "Male", "Club", new ArrayList<>(), new ArrayList<>());

        when(athleteService.getAthlete(1L)).thenReturn(Optional.of(athlete));
        when(athleteService.addAthlete(athlete)).thenReturn(athlete);
        doNothing().when(athleteService).deleteAthlete(1L);

        mockMvc.perform(post("/athletes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(athlete)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/athletes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(athleteService, times(1)).deleteAthlete(1L);
    }
}