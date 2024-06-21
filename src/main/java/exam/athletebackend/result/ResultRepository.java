package exam.athletebackend.result;

import exam.athletebackend.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByDiscipline(Discipline discipline);
}
