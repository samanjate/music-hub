package edu.neu.cs5200.orm.jpa.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArtistService {
	
	@Autowired
	ArtistRepository artistRepository;
	
	Session sessionManager = Session.getInstance();
	
	@PostMapping("/api/artist")
	public Artist createArtist(@RequestBody Person person, HttpSession session) {
		Artist createdArtist = artistRepository.save(new Artist(person));
		sessionManager.setSession(session, createdArtist);
		return createdArtist;
	}
	
	@GetMapping("/api/artist")
	public List<Artist> findAllArtists() {
		return (List<Artist>) artistRepository.findAll();
	}
	
	@GetMapping("/api/artist/{aid}")
	public Artist findArtistById(@PathVariable("aid") int aid) {
		Optional<Artist> oArtist = artistRepository.findById(aid);
		if(oArtist.isPresent()) {
			return oArtist.get();
		} else {
			return null;
		}
	}
	
	@PutMapping("/api/artist/{aid}")
	public Artist updateArtist(@RequestBody Artist artist, @PathVariable("aid") int aid, HttpSession session) {
		Optional<Artist> oArtist = artistRepository.findById(aid);
		if(oArtist.isPresent()) {
			artist.setId(oArtist.get().getId());
			Artist updatedArtist = artistRepository.save(artist);
			sessionManager.setSession(session, updatedArtist);
			return updatedArtist;
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/api/artist/{aid}")
	public void deleteArtist(@RequestBody Artist artist, @PathVariable("aid") int aid, HttpSession session) {
		artistRepository.deleteById(aid);
		sessionManager.clearSession(session);
	}

}
