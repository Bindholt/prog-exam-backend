package exam.athletebackend.config;

import exam.athletebackend.athlete.Athlete;
import exam.athletebackend.athlete.AthleteRepository;
import exam.athletebackend.discipline.Discipline;
import exam.athletebackend.discipline.DisciplineRepository;
import exam.athletebackend.result.Result;
import exam.athletebackend.result.ResultRepository;
import exam.athletebackend.result.ResultService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static exam.athletebackend.result.ResultType.*;

@Component
@Profile("!test")
public class InitData implements CommandLineRunner {
    private final AthleteRepository athleteRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;
    private final ResultService resultService;

    public InitData(AthleteRepository athleteRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository, ResultService resultService) {
        this.athleteRepository = athleteRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
        this.resultService = resultService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(athleteRepository.count() == 0) {
            createAthletes();
        }
        if(disciplineRepository.count() == 0) {
            createDisciplines();
        }
        if(resultRepository.count() == 0) {
            createResults();
        }
    }

    private void createDisciplines() {
        disciplineRepository.save(new Discipline("100-meter run", TIME));
        disciplineRepository.save(new Discipline("High jump", DISTANCE));
        disciplineRepository.save(new Discipline("Long jump", DISTANCE));
        disciplineRepository.save(new Discipline("Marathon", TIME));
        disciplineRepository.save(new Discipline("Shot put", DISTANCE));
        disciplineRepository.save(new Discipline("100-meter hurdles", TIME));
        disciplineRepository.save(new Discipline("Pole vault", DISTANCE));
        disciplineRepository.save(new Discipline("Swimming 200m freestyle", TIME));
        disciplineRepository.save(new Discipline("Gymnastics floor routine", POINTS));
    }

    private void createAthletes() {
        athleteRepository.save(new Athlete("Julie", LocalDate.of(1998, 8, 31), "Kvinde", "Musserne"));
        athleteRepository.save(new Athlete("Mads", LocalDate.of(1992, 12, 12), "Mand", "Løberne"));
        athleteRepository.save(new Athlete("Lars", LocalDate.of(1988, 4, 2), "Mand", "Hold3"));
        athleteRepository.save(new Athlete("Mette", LocalDate.of(1995, 6, 15), "Kvinde", "Hold4"));
        athleteRepository.save(new Athlete("Sofie", LocalDate.of(1999, 2, 28), "Kvinde", "Hold4"));
        athleteRepository.save(new Athlete("Mikkel", LocalDate.of(1990, 10, 10), "Mand", "Hold4"));
    }

    private void createResults() {
        Optional<Athlete> julie = athleteRepository.findByName("Julie");
        julie.ifPresent(athlete -> {
            Discipline discipline = disciplineRepository.findByName("100-meter run").orElse(null);
            if (discipline != null) {
                Result result = new Result(TIME, LocalDate.now(), "12312", athlete, discipline);
                athlete.getResults().add(result);
                athlete.getDisciplines().add(discipline);
                discipline.getAthletes().add(athlete);
                discipline.getResults().add(result);

                resultRepository.save(result);
                athleteRepository.save(athlete);
                disciplineRepository.save(discipline);
            }
        });

        Optional<Athlete> mads = athleteRepository.findByName("Mads");
        mads.ifPresent(athlete -> {
            Discipline discipline = disciplineRepository.findByName("High jump").orElse(null);
            if (discipline != null) {
                Result result = new Result(DISTANCE, LocalDate.now(), "1.23", athlete, discipline);
                athlete.getResults().add(result);
                athlete.getDisciplines().add(discipline);
                discipline.getAthletes().add(athlete);
                discipline.getResults().add(result);

                resultRepository.save(result);
                athleteRepository.save(athlete);
                disciplineRepository.save(discipline);
            }
        });

        Optional<Athlete> lars = athleteRepository.findByName("Lars");
        lars.ifPresent(athlete -> {
            Discipline discipline = disciplineRepository.findByName("Long jump").orElse(null);
            if (discipline != null) {
                Result result = new Result(DISTANCE, LocalDate.now(), "1.23", athlete, discipline);
                athlete.getResults().add(result);
                athlete.getDisciplines().add(discipline);
                discipline.getAthletes().add(athlete);
                discipline.getResults().add(result);

                resultRepository.save(result);
                athleteRepository.save(athlete);
                disciplineRepository.save(discipline);
            }
        });

        Optional<Athlete> mette = athleteRepository.findByName("Mette");
        mette.ifPresent(athlete -> {
            Discipline discipline = disciplineRepository.findByName("Marathon").orElse(null);
            if (discipline != null) {
                Result result = new Result(TIME, LocalDate.now(), "12312", athlete, discipline);
                athlete.getResults().add(result);
                athlete.getDisciplines().add(discipline);
                discipline.getAthletes().add(athlete);
                discipline.getResults().add(result);

                resultRepository.save(result);
                athleteRepository.save(athlete);
                disciplineRepository.save(discipline);
            }
        });
    }
}
