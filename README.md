# Programmerings eksamen juni 2024 - Backend
By Christian Bindholt

Made with [Spring Boot](https://spring.io/projects/spring-boot) and [Maven](https://maven.apache.org/)

Frontend repository: [prog-exam-frontend](https://github.com/bindholt/prog-exam-frontend)

## Setup
Clone the repository and enter the directory
```bash
git clone https://github.com/bindholt/prog-exam-backend.git
cd prog-exam-backend
```

in `src/main/resources` create a file called `env.properties` and add the following properties
```properties
DB_URL=your_db_url
DB_USER=your_db_username
DB_PASS=your_db_password
```

Open the project in IntelliJ IDEA and run application from the following class
```java 
src.main.java.exam.athletebackend.AthleteApplication
```

You can now access the API at `http://localhost:8080`