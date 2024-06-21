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
        //deleteAllResults();
        //deleteAllDisciplines();
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
        disciplineRepository.save(new Discipline("Chess", POINTS));
        disciplineRepository.save(new Discipline("Gymnastics floor routine", POINTS));
    }

    private void createAthletes() {
        athleteRepository.save(new Athlete("Julie", LocalDate.of(1998, 8, 31), "Kvinde", "Musserne"));
        athleteRepository.save(new Athlete("Mads", LocalDate.of(1992, 12, 12), "Mand", "Løb"));
        athleteRepository.save(new Athlete("Lars", LocalDate.of(1988, 4, 2), "Mand", "Cykling"));
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
    }

    public void deleteAllResults() {
        List<Result> results = resultRepository.findAll();
        for (Result result : results) {
            Athlete athlete = result.getAthlete();
            athlete.removeResult(result);
            athleteRepository.save(athlete);
            Discipline discipline = result.getDiscipline();
            discipline.removeResult(result);
            disciplineRepository.save(discipline);
        }
        resultRepository.deleteAll();
    }

    public void deleteAllDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        for (Discipline discipline : disciplines) {
            // Remove discipline from each athlete
            List<Athlete> athletes = athleteRepository.findAllByDisciplinesContains(discipline);
            for (Athlete athlete : athletes) {
                athlete.removeDiscipline(discipline);
                discipline.getAthletes().remove(athlete);
                athleteRepository.save(athlete);
            }

            // Remove discipline from each result
            List<Result> results = resultRepository.findAllByDiscipline(discipline);
            for (Result result : results) {
                result.setDiscipline(null);
                resultRepository.save(result);
            }

            disciplineRepository.save(discipline);
        }
        disciplineRepository.deleteAll();
    }
}
