package exam.athletebackend.athlete;

import exam.athletebackend.athlete.dtos.AthleteRequestDTO;
import exam.athletebackend.athlete.dtos.AthleteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
public class AthleteController {
    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @GetMapping
    public ResponseEntity<List<AthleteResponseDTO>> getAthletes() {
        return ResponseEntity.ok(athleteService.getAthletes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteResponseDTO> getAthlete(@PathVariable Long id) {
        return ResponseEntity.of(athleteService.getAthlete(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AthleteResponseDTO>> getAthleteByName(@PathVariable String name) {
        return ResponseEntity.ok(athleteService.getAthleteByName(name));
    }

    @PostMapping
    public ResponseEntity<AthleteResponseDTO> addAthlete(@RequestBody AthleteRequestDTO dto) {
        return ResponseEntity.ok(athleteService.addAthlete(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AthleteResponseDTO> updatePartialAthlete(@PathVariable Long id, @RequestBody AthleteRequestDTO dto) {
        return ResponseEntity.of(athleteService.updatePartialAthlete(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.ok().build();
    }
}
