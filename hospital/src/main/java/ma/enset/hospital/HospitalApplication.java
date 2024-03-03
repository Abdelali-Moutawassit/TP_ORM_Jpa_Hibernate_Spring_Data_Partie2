package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsultationRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
	@Bean
	CommandLineRunner start(IHospitalService hospitalService ,
							PatientRepository patientRepository,
							MedecinRepository medecinRepository,
							RendezVousRepository rendezVousRepository,
							ConsultationRepository consultationRepository){
		 return args -> {
			 Stream.of("Abdo","Ayoub","Hassaaan")
					 .forEach(name->{
						 Patient patient = new Patient();
						 patient.setNom(name);
						 patient.setDateNaissance(new Date());
						 patient.setMalade(false);
						 hospitalService.savePatient(patient);
					 });
			 Stream.of("Sami","Amine","Abderrahman")
					 .forEach(name->{
						 Medecin medecin = new Medecin();
						 medecin.setNom(name);
						 medecin.setEmail(name+"@gmail.com");
						 medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
						 hospitalService.saveMedecin(medecin);
					 });

			 Patient patient = patientRepository.findById(1L).orElse(null);
			 Patient patient1 = patientRepository.findByNom("Abdo");

			 Medecin medecin = medecinRepository.findByNom("Sami");

			 RendezVous rendezVous = new RendezVous();
			 rendezVous.setDate(new Date());
			 rendezVous.setStatus(StatusRDV.PENDING);
			 rendezVous.setMedecin(medecin);
			 rendezVous.setPatient(patient);
			 hospitalService.saveRDV(rendezVous);


             RendezVous rendezVous1 = rendezVousRepository.findById(1L).orElse(null);
			 Consultation consultation = new Consultation();
			 consultation.setDateConsultation(new Date());
			 consultation.setRendezVous(rendezVous1);
			 consultation.setRapport("Rapport de la consultation ........");
             hospitalService.saveConsultation(consultation);


		 };


	}

}
