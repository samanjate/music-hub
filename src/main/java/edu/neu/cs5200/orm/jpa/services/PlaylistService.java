package edu.neu.cs5200.orm.jpa.services;

import java.util.List;

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
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlaylistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlaylistService {

	@Autowired
	PlaylistRepository playlistRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	Session sessionManager = Session.getInstance();

	@PostMapping("/create/playlist")
	public ResponseEntity<String> createPlaylist(@RequestParam("file") MultipartFile file,
			@RequestParam("playlist") String playlist) {
		String message = "";
		try {
			int loggedInUserId = sessionManager.checkSession().getId();
			ObjectMapper mapper = new ObjectMapper();
			Playlist obj = mapper.readValue(playlist, Playlist.class);
			obj.setCreatedBy(personRepository.findById(loggedInUserId).get());
			Playlist createdPlaylist = playlistRepository.save(obj);
			S3Connection.getInstance()
					.uploadFilesInFolder( loggedInUserId + "/" + createdPlaylist.getId(), file);
			
			createdPlaylist.setImageURL(S3Connection.AWS_BUCKET_URL + loggedInUserId + "/" + createdPlaylist.getId()+"/" + file.getOriginalFilename());
			playlistRepository.save(createdPlaylist);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	@GetMapping("/api/playlist/person/{personId}")
	public List<Playlist> findTracksByArtistId(@PathVariable("personId") int personId) {
		return personRepository.findPlaylistsByPersonId(personId);
	}
}
