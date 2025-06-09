package com.isi.patient;

import com.isi.patient.model.Patient;
import com.isi.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(PatientRepository patientRepository) {
		return args -> {
			patientRepository.save(Patient.builder()
					.nom("Ka")
					.prenom("Moussa")
					.dateNaissance(LocalDate.of(1985, 5, 15))
					.sexe("M")
					.adresse("123 Rue des Jardins, Dakar")
					.telephone("771234567")
					.email("moussa.ka@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Diop")
					.prenom("Amina")
					.dateNaissance(LocalDate.of(1990, 8, 22))
					.sexe("F")
					.adresse("456 Avenue Liberté, Thiès")
					.telephone("776543219")
					.email("amina.diop@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Ndiaye")
					.prenom("Fatou")
					.dateNaissance(LocalDate.of(2018, 5, 15))
					.sexe("F")
					.adresse("789 Rue de Thiaroye, Pikine")
					.telephone("771112233")
					.email("parent.ndiaye@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Ndiaye")
					.prenom("Ibrahima")
					.dateNaissance(LocalDate.of(1978, 3, 10))
					.sexe("M")
					.adresse("789 Boulevard du Soleil, Saint-Louis")
					.telephone("781234567")
					.email("ibrahima.ndiaye@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Fall")
					.prenom("Fatou")
					.dateNaissance(LocalDate.of(1995, 11, 30))
					.sexe("F")
					.adresse("321 Rue de la Plage, Mbour")
					.telephone("775678912")
					.email("fatou.fall@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Gueye")
					.prenom("Modou")
					.dateNaissance(LocalDate.of(1982, 7, 5))
					.sexe("M")
					.adresse("654 Allée des Baobabs, Kaolack")
					.telephone("772345678")
					.email("modou.gueye@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Sow")
					.prenom("Mariama")
					.dateNaissance(LocalDate.of(1988, 8, 8))
					.sexe("F")
					.adresse("Immeuble Salam, Appt 12, Rue 10 x 12, Ouakam")
					.telephone("772345679")
					.email("mariama.sow@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Diallo")
					.prenom("Ousmane")
					.dateNaissance(LocalDate.of(1975, 2, 28))
					.sexe("M")
					.adresse("Quartier Escale, Saint-Louis")
					.telephone("762345678")
					.email("ousmane.diallo@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Kane")
					.prenom("Rokhaya")
					.dateNaissance(LocalDate.of(2000, 12, 1))
					.sexe("F")
					.adresse("Cité Université, Dakar")
					.telephone("781112233")
					.email("rok.kane@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Mbengue Fall")
					.prenom("Jean-Jacques")
					.dateNaissance(LocalDate.of(1993, 4, 18))
					.sexe("M")
					.adresse("Résidence les Cocotiers, Saly")
					.telephone("773334455")
					.email("jj.mbengue@example.com")
					.build());

			patientRepository.save(Patient.builder()
					.nom("Fall")
					.prenom("Moustapha")
					.dateNaissance(LocalDate.of(1955, 11, 25))
					.sexe("M")
					.adresse("456 Rue des Almadies, Dakar")
					.telephone("761234567")
					.email("m.fall@example.com")
					.build());
		};
	}
}
