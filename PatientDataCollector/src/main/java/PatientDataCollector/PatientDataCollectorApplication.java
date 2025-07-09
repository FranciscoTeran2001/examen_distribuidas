package PatientDataCollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PatientDataCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientDataCollectorApplication.class, args);
	}

}
