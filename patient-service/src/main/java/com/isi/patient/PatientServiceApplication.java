package com.isi.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(PatientRepository repository) {
//		return args -> {
//			Patient patient = Patient.builder()
//					.nom("Kaba")
//					.prenom("Ismail")
//					.sexe("Masculin")
//					.adresse("Rue de la Rochette")
//					.telephone("0612345678")
//					.email("abakaba@gmail.com")
//					.build();
//			Patient patient1 = Patient.builder()
//					.nom("Kallo")
//					.prenom("Karim")
//					.sexe("Masculin")
//					.adresse("Rue de la Rochette")
//					.telephone("0612340678")
//					.email("kallo@gmail.com")
//					.build();
//			repository.save(patient);
//			repository.save(patient1);
//
//		};
//	}

}
