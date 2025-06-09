package com.isi.dossier;

import com.isi.dossier.model.Dossier;
import com.isi.dossier.repository.DossierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DossierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DossierServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDossiers(DossierRepository dossierRepository) {
		return args -> {
			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 1, 15))
					.compteRendu("Consultation annuelle - bilan de santé normal")
					.patientId(1L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 2, 20))
					.compteRendu("Suivi hypertension - tension stabilisée")
					.patientId(2L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 3, 5))
					.compteRendu("Vaccination Pentavalent + ROR")
					.patientId(3L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 5, 12))
					.compteRendu("Consultation pour fièvre - diagnostic: infection virale")
					.patientId(4L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 4, 8))
					.compteRendu("ECG anormal - demande d'échocardiogramme")
					.patientId(5L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 4, 22))
					.compteRendu("Résultats écho: insuffisance mitrale légère")
					.patientId(1L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 6, 10))
					.compteRendu("Contrôle 1 mois après appendicectomie - cicatrisation complète")
					.patientId(2L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 6, 15))
					.compteRendu("Consultation prénatale - 24 SA - écho morphologique normale")
					.patientId(3L)
					.build());

			dossierRepository.save(Dossier.builder()
					.dateConsultation(LocalDate.of(2024, 6, 18))
					.compteRendu("Urgence pédiatrique - crise d'asthme - traitement par nébulisation")
					.patientId(4L)
					.build());
		};
	}

}
