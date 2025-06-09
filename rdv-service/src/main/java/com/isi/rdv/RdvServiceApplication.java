package com.isi.rdv;

import com.isi.rdv.model.Rdv;
import com.isi.rdv.repository.RdvRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class RdvServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdvServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(RdvRepository rdvRepository) {
		return args -> {
			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 15, 9, 0))
					.motif("Consultation annuelle")
					.patientId(1L)
					.medecinId(3L)
					.build());
			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 16, 10, 30))
					.motif("Vaccination")
					.patientId(2L)
					.medecinId(5L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 17, 14, 0))
					.motif("Suivi hypertension")
					.patientId(3L)
					.medecinId(7L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 18, 8, 30))
					.motif("Douleurs abdominales")
					.patientId(4L)
					.medecinId(2L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 19, 11, 0))
					.motif("Suivi grossesse")
					.patientId(5L)
					.medecinId(3L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 20, 15, 30))
					.motif("Examen de peau")
					.patientId(6L)
					.medecinId(1L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 21, 16, 45))
					.motif("Contrôle myopie")
					.patientId(7L)
					.medecinId(4L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 22, 9, 15))
					.motif("Suivi fracture")
					.patientId(8L)
					.medecinId(6L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 23, 14, 30))
					.motif("Suivi dépression")
					.patientId(9L)
					.medecinId(2L)
					.build());

			rdvRepository.save(Rdv.builder()
					.date(LocalDateTime.of(2024, 6, 24, 10, 0))
					.motif("Détartrage")
					.patientId(10L)
					.medecinId(1L)
					.build());
		};
	}

}
