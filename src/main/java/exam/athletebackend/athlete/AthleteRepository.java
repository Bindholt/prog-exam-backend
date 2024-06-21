package exam.athletebackend.athlete;

import exam.athletebackend.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<List<Athlete>> findAllByNameContains(String s);
    Optional<Athlete> findByName(String s);

    List<Athlete> findAllByDisciplinesContains(Discipline discipline);
}
