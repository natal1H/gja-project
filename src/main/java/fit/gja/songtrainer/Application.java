package fit.gja.songtrainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Represents Spring Boot application
 */
@SpringBootApplication
@EnableJpaRepositories
@ConfigurationPropertiesScan
public class Application {
    /**
     * Main method that launches the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
