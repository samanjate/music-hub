package edu.neu.cs5200.orm.jpa.services;

import java.util.ArrayList;
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
	
	@GetMapping("/api/person/username/{username}")
	public List<Person> findPersonByUsername(@PathVariable String username) {
		return personRepository.findUserByUsername("%"+username+"%");
	}
	
	@GetMapping("/api/person/following/{personId}")
	public List<Person> findPersonFollowing(@PathVariable int personId) {
		return personRepository.findUserFollowing(personId);
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
	
	@PostMapping("/api/follow/{personId}")
	public ResponseEntity<HttpStatus> followPerson(@PathVariable("personId") int personFollowedById, @RequestBody Person person){
		
		Person p = personRepository.findById(personFollowedById).get();
		person = personRepository.findById(person.getId()).get();
		
		List<Person> followersList = person.getFollowers();
		if(followersList==null) {
			followersList = new ArrayList<>();
		}
		followersList.add(p);
		person.setFollowers(followersList);
		personRepository.save(person);
		
		List<Person> followingList = p.getFollowing();
		if(followingList==null) {
			followingList = new ArrayList<>();
		}
		followingList.add(person);
		p.setFollowing(followingList);
		personRepository.save(p);

		return ResponseEntity.ok(HttpStatus.OK);	
	}
	
	
	@PostMapping("/api/unfollow/{personId}")
	public ResponseEntity<HttpStatus> unfollowPerson(@PathVariable("personId") int personUnfollowedById, @RequestBody Person person){
		
		Person p = personRepository.findById(personUnfollowedById).get();
		person = personRepository.findById(person.getId()).get();
		
		List<Person> newFollowersList;
		newFollowersList = person.getFollowers();
		for(Person pr:newFollowersList) {
			if(pr.getId() == personUnfollowedById) {
				newFollowersList.remove(pr);
				break;
			}
		}
		person.setFollowers(newFollowersList);
		personRepository.save(person);

		List<Person> newFollowingList;
		newFollowingList = p.getFollowing();
		for(Person pr:newFollowingList) {
			if(pr.getId() == person.getId()) {
				newFollowingList.remove(pr);
				break;
			}
		}
		p.setFollowing(newFollowingList);
		personRepository.save(p);
		
		return ResponseEntity.ok(HttpStatus.OK);	
	}
}
