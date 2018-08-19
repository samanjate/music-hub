package edu.neu.cs5200.orm.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.neu.cs5200.aws.S3Connection;
import edu.neu.cs5200.orm.jpa.entities.Album;
import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.repositories.AlbumRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlbumService {

	@Autowired
	AlbumRepository albumRepository;

	@Autowired
	ArtistRepository artistRepository;
	
	Session sessionManager = Session.getInstance();

	@GetMapping("/api/album/artist/{artistId}")
	public List<Album> findAlbumsByArtistId(@PathVariable("artistId") int artistId) {
		Artist a = new Artist();
		a.setId(artistId);
		return albumRepository.findAlbumsByArtistId(a);
	}
	
	@GetMapping("/api/album/name/{name}")
	public List<Album> findAlbumsByAlbumName(@PathVariable("name") String name) {
		return albumRepository.findAlbumsByName("%"+name+"%");
	}
	
	@GetMapping("/api/album/{aid}")
	public Album findAlbumById(@PathVariable("aid") int aid) {
		Optional<Album> oAlbum = albumRepository.findById(aid);
		if(oAlbum.isPresent()) {
			return oAlbum.get();
		}
		return null;
	}
	
	@PostMapping("/create/album")
	public ResponseEntity<String> createTrack(@RequestParam("file") MultipartFile file,
			@RequestParam("album") String album) {
		String message = "";
		try {
			int loggedInUserId = sessionManager.checkSession().getId();
			ObjectMapper mapper = new ObjectMapper();
			Album obj = mapper.readValue(album, Album.class);
			obj.setArtist(artistRepository.findById(loggedInUserId).get());
			Album createdAlbum = albumRepository.save(obj);
			S3Connection.getInstance()
					.uploadFilesInFolder( loggedInUserId + "/" + createdAlbum.getId(), file);
			
			createdAlbum.setImageURL(S3Connection.AWS_BUCKET_URL + loggedInUserId + "/" + createdAlbum.getId()+"/" + file.getOriginalFilename());
			albumRepository.save(createdAlbum);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	
}
