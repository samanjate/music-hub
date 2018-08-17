package edu.neu.cs5200.orm.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Album;
import edu.neu.cs5200.orm.jpa.repositories.AlbumRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlbumService {

	@Autowired
	AlbumRepository albumRepository;
	
	@Autowired
	ArtistRepository artistRepository;
	
	
	@GetMapping("/api/album/artist/{artistId}")
	public List<Album> findAlbumsByArtistId(@PathVariable("artistId") int artistId) {
		return artistRepository.findAlbumsByArtistId(artistId);
	}
	
}
