package exam.athletebackend.result;

import exam.athletebackend.result.dtos.ResultRequestDTO;
import exam.athletebackend.result.dtos.ResultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<ResultResponseDTO> addResult(@RequestBody ResultRequestDTO resultRequestDTO) {
        ResultResponseDTO resultResponseDTO = resultService.addResult(resultRequestDTO);
        return ResponseEntity.ok(resultResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> updatePartialResult(@PathVariable Long id, @RequestBody ResultRequestDTO dto) {
        return ResponseEntity.of(resultService.updatePartialResult(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.ok().build();
    }
}
