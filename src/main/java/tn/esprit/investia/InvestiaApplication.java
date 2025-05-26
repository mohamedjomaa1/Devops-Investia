package tn.esprit.investia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;



//---MOOTEZ IMPORTS--------------
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class InvestiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestiaApplication.class, args);
	}

	//-------------------------MOOTEZ MAIN----------------------
	/**
	 * Configure Jackson (le convertisseur JSON/Objet) pour gérer correctement
	 * les types Date/Heure de Java 8+ (LocalDateTime, LocalDate, etc.).
	 * @return Un customizer pour Jackson.
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> {
			// Enregistre le module pour les types Java 8 Date/Time
			builder.modules(new JavaTimeModule());
			// Optionnel : Configurer d'autres fonctionnalités Jackson ici
			// Par exemple, pour écrire les dates comme des chaînes ISO (ex: "2025-04-19T22:15:30")
			// builder.featuresToDisable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		};
	}

	/*
	 * === NOTE IMPORTANTE SUR RestTemplate ===
	 * Le Bean RestTemplate a été déplacé dans la classe de configuration dédiée 'AppConfig.java'.
	 * Il ne doit être défini qu'à UN SEUL endroit.
	 * Si AppConfig.java n'existe pas ou ne contient pas le bean RestTemplate,
	 * décommentez le code ci-dessous. L'emplacement recommandé est AppConfig.java.
	 */

	 @Bean
	public RestTemplate restTemplate() {
		 return new RestTemplate();
	}



}
