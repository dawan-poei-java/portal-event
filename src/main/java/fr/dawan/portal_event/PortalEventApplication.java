package fr.dawan.portal_event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
	info= @Info(
		title = "Portal Event API",
		version = "1.0",
		description = "Documentation Portal Event API"
	)
)
@SpringBootApplication
public class PortalEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalEventApplication.class, args);
	}

}
