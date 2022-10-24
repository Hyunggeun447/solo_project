package solo_project.solo_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SoloProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoloProjectApplication.class, args);
	}

}
