package edu.neu.cs5200.orm.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.repositories.PlaylistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlaylistService {

	@Autowired
	PlaylistRepository playlistRepository;
	
	@GetMapping("/api/playlist/person/{personId}")
	public List<Playlist> findTracksByArtistId(@PathVariable("personId") int personId) {
		Person p = new Person();
		p.setId(personId);
		return playlistRepository.findPlaylistsByPersonId(p);
	}
}
