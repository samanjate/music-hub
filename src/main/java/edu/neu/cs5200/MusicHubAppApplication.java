package edu.neu.cs5200;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.AdminRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@SpringBootApplication
public class MusicHubAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MusicHubAppApplication.class, args);
	}

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public void run(String... args) throws Exception {
		List<Person> users = personRepository.findUserByUsername("admin");
		if(users.isEmpty()) {
			Admin admin = new Admin();
			admin.setUsername("admin");
			admin.setPassword("admin");
			adminRepository.save(admin);
		}
	}
}
