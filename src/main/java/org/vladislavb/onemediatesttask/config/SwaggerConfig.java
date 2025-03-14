package org.vladislavb.onemediatesttask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up Swagger (OpenAPI) documentation.
 * Provides metadata and server information for the OneMedia Test Task API.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures the OpenAPI bean for Swagger UI.
     *
     * @return OpenAPI instance with configured metadata and server details.
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server")
                ))
                .info(new Info()
                        .title("OneMedia Test Task API")
                        .description("API for managing user authentication and authorization, " +
                                "using JWT for secure access.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vladislav Baryshev")
                                .email("vladislavbaryshev97@gmail.com")
                        )
                );
    }
}

