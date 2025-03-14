package org.vladislavb.onemediatesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The main entry point for the OneMediaTestTask application.
 * This class bootstraps the Spring Boot application and defines the required beans for the application.
 *
 * @author  Vladislav Baryshev
 */
@SpringBootApplication
public class OneMediaTestTaskApplication {

    /**
     * The main method to run the Spring Boot application.
     *
     * @param args the command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(OneMediaTestTaskApplication.class, args);
    }

    /**
     * Defines a {@link RestTemplate} bean for making HTTP requests.
     *
     * @return a new instance of {@link RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
