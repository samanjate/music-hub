package edu.neu.cs5200.orm.jpa.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Advertiser;
import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.entities.Critic;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.AdvertiserRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;
import edu.neu.cs5200.orm.jpa.repositories.CriticRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.orm.jpa.types.userType;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	CriticRepository criticRepository;
	
	@Autowired
	AdvertiserRepository advertiserRepository;
	
	Session sessionManager = Session.getInstance();

	@GetMapping("/api/person")
	public List<Person> findAllPersons() {
		return (List<Person>) personRepository.findAll();
	}

	@Deprecated
	@PostMapping("/api/register/{type}")
	public Person createPerson(@RequestBody Person person, @PathVariable("type") String type, HttpSession session) {

		if (type.equalsIgnoreCase(userType.ARTIST.toString())) {
			Artist createdArtist = artistRepository.save(new Artist(person));
			sessionManager.setSession(session, createdArtist);
			return createdArtist;
		} else if(type.equalsIgnoreCase(userType.CRITIC.toString())) {
			Critic createdCritic = criticRepository.save(new Critic(person));
			sessionManager.setSession(session, createdCritic);
			return createdCritic;
		} else if(type.equalsIgnoreCase(userType.ADVERTISER.toString())) {
			Advertiser createdAdvertiser = advertiserRepository.save(new Advertiser(person));
			sessionManager.setSession(session, createdAdvertiser);
			return createdAdvertiser;
		}
		return null;
	}

	@GetMapping("/api/login")
	public ResponseEntity<HttpStatus> findUserByCredentials(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		List<Person> persons = personRepository.findUserByCredentials(username, password);
		if (persons.isEmpty()) {
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
		}
		sessionManager.setSession(session, persons.get(0));  // store user in currentUser
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/api/get/session")
	public Optional<Person> checkSession() {
		if(sessionManager.checkSession() != null) {
			return personRepository.findById(sessionManager.checkSession().getId());
		} else {
			return null;
		}
	}
	
	@GetMapping("/api/logout")
	public ResponseEntity<HttpStatus> logout(HttpSession session) {
		sessionManager.clearSession(session);
		if(sessionManager.checkSession() == null) {
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
		}
	}
}
