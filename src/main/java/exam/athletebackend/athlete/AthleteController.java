package exam.athletebackend.athlete;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<AthleteDTO>> getAthletes() {
        return ResponseEntity.ok(athleteService.getAthletes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteDTO> getAthlete(@PathVariable Long id) {
        return ResponseEntity.of(athleteService.getAthlete(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<AthleteDTO>> getAthleteByName(@PathVariable String name) {
        return ResponseEntity.ok(athleteService.getAthleteByName(name));
    }

    @PostMapping
    public ResponseEntity<AthleteDTO> addAthlete(@RequestBody AthleteDTO athleteDTO) {
        return ResponseEntity.ok(athleteService.addAthlete(athleteDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AthleteDTO> updatePartialAthlete(@PathVariable Long id, @RequestBody AthleteDTO dto) {
        return ResponseEntity.of(athleteService.updatePartialAthlete(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.ok().build();
    }
}
