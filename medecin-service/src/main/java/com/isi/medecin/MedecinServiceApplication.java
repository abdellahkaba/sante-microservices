package com.isi.medecin;

import com.isi.medecin.model.Medecin;
import com.isi.medecin.repository.MedecinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MedecinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedecinServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(MedecinRepository medecinRepository) {
		return args -> {
			medecinRepository.save(Medecin.builder()
					.nom("Ndiaye")
					.prenom("Abdoulaye")
					.specialite("Cardiologie")
					.telephone("77 888 99 00")
					.email("abdoulaye.ndiaye@hopital.sn")
					.adresseCabinet("Centre Médical de Dakar, 2ème étage")
					.build());

			medecinRepository.save(Medecin.builder()
					.nom("Fall")
					.prenom("Aminata")
					.specialite("Pédiatrie")
					.telephone("76 543 21 00")
					.email("aminata.fall@hopital.sn")
					.adresseCabinet("Clinique des Enfants, Plateau")
					.build());

			medecinRepository.save(Medecin.builder()
					.nom("Diallo")
					.prenom("Mamadou")
					.specialite("Neurologie")
					.telephone("70 123 45 67")
					.email("mamadou.diallo@hopital.sn")
					.adresseCabinet("Hôpital Principal, Service Neurologie")
					.build());

			medecinRepository.save(Medecin.builder()
					.nom("Sow")
					.prenom("Fatima")
					.specialite("Gynécologie")
					.telephone("78 987 65 43")
					.email("fatima.sow@hopital.sn")
					.adresseCabinet("Clinique de la Femme, Avenue Cheikh Anta Diop")
					.build());


			medecinRepository.save(Medecin.builder()
					.nom("Diop")
					.prenom("Cheikh")
					.specialite("Chirurgie Orthopédique")
					.telephone("77 654 32 10")
					.email("cheikh.diop@hopital.sn")
					.adresseCabinet("Pavillon Traumatologie, Hôpital Fann")
					.build());

			medecinRepository.save(Medecin.builder()
					.nom("Gueye")
					.prenom("Aïssatou")
					.specialite("Dermatologie")
					.telephone("76 789 01 23")
					.email("a.gueye@hopital.sn")
					.adresseCabinet("Centre de Dermatologie, Almadies")
					.build());

			medecinRepository.save(Medecin.builder()
					.nom("Kane")
					.prenom("Moussa")
					.specialite("Ophtalmologie")
					.telephone("70 456 78 90")
					.email("moussa.kane@hopital.sn")
					.adresseCabinet("Cabinet Vision Plus, Sacré-Cœur 3")
					.build());
		};
	}
}
