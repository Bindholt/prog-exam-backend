package exam.athletebackend.discipline;

import exam.athletebackend.athlete.Athlete;
import exam.athletebackend.result.Result;
import exam.athletebackend.result.ResultType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private ResultType resultType;
    @ManyToMany(mappedBy = "disciplines", fetch = FetchType.EAGER)
    private List<Athlete> athletes = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Result> results = new ArrayList<>();

    public Discipline(String name, ResultType resultType) {
        this.name = name;
        this.resultType = resultType;
    }

    public void addAthlete(Athlete athlete) {
        athletes.add(athlete);
        athlete.getDisciplines().add(this);
    }

    public void removeAthlete(Athlete athlete) {
        athletes.remove(athlete);
        athlete.getDisciplines().remove(this);
    }

    public void addResult(Result result) {
        results.add(result);
        result.setDiscipline(this);
    }

    public void removeResult(Result result) {
        results.remove(result);
        result.setDiscipline(null);
    }
}
