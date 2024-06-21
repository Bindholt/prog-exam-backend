package exam.athletebackend.result;

import exam.athletebackend.athlete.Athlete;
import exam.athletebackend.discipline.Discipline;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ResultType resultType;
    private LocalDate date;
    private String result;
    @ManyToOne
    private Athlete athlete;
    @ManyToOne
    private Discipline discipline;

    public Result(ResultType resultType, LocalDate date, String result, Athlete athlete, Discipline discipline) {
        this.resultType = resultType;
        this.date = date;
        this.result = result;
        this.athlete = athlete;
        this.discipline = discipline;
    }
}
