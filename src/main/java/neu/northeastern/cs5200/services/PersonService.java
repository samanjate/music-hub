package neu.northeastern.cs5200.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import neu.northeastern.cs5200.models.Artist;
import neu.northeastern.cs5200.models.Person;
import neu.northeastern.cs5200.repositories.ArtistRepository;
import neu.northeastern.cs5200.repositories.PersonRepository;

@RestController
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ArtistRepository artistRepository;
	
	@GetMapping("/api/all/person")
	public List<Person> findAllPersons() {
		return (List<Person>) personRepository.findAll();
	}
	
	@PostMapping("/api/artist")
	public Artist createArtist(@RequestBody Artist artist) {
		return artistRepository.save(artist);
	}
	
	
}
