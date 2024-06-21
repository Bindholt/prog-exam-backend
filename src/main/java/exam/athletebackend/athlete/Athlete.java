package exam.athletebackend.athlete;

import exam.athletebackend.discipline.Discipline;
import exam.athletebackend.result.Result;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String gender;
    private String club;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Result> results = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "athlete_discipline",
        joinColumns = @JoinColumn(name = "athlete_id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    private List<Discipline> disciplines = new ArrayList<>();

    public Athlete(String name, LocalDate birthdate, String gender, String club, List<Discipline> disciplines) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.club = club;
        this.disciplines = disciplines;
    }

    public Athlete(String name, LocalDate birthdate, String gender, String club) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.club = club;
    }


    public void addDiscipline(Discipline discipline) {
        if (!disciplines.contains(discipline)) {
            disciplines.add(discipline);
            discipline.getAthletes().add(this);
        }
    }

    public void removeDiscipline(Discipline discipline) {
        disciplines.remove(discipline);
        discipline.getAthletes().remove(this);
    }

    public void addResult(Result result) {
        results.add(result);
        result.setAthlete(this);
    }

    public void removeResult(Result result) {
        results.remove(result);
        result.setAthlete(null);
    }
}
