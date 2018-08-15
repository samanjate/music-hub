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
import edu.neu.cs5200.orm.jpa.entities.Track;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;
import edu.neu.cs5200.orm.jpa.repositories.TrackRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrackService {

	@Autowired
	TrackRepository trackRepository;
	
	@Autowired
	ArtistRepository artistRepository;

	
	Session sessionManager = Session.getInstance();

	@PostMapping("/create/track")
	public ResponseEntity<String> createTrack(@RequestParam("file") MultipartFile file,
			@RequestParam("track") String track) {
		String message = "";
		try {
			int loggedInUserId = sessionManager.checkSession().getId();
			ObjectMapper mapper = new ObjectMapper();
			Track obj = mapper.readValue(track, Track.class);
			obj.setArtist(artistRepository.findById(loggedInUserId).get());
			Track createdTrack = trackRepository.save(obj);
			S3Connection.getInstance()
					.uploadFilesInFolder( loggedInUserId + "/" + createdTrack.getId(), file);
			
			createdTrack.setPreviewURL(S3Connection.AWS_BUCKET_URL + loggedInUserId + "/" + createdTrack.getId()+"/" + file.getOriginalFilename() +".mp3");
			trackRepository.save(createdTrack);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping("/api/track/{name}")
	public List<Track> findTracksByName(@PathVariable("name") String name) {
		return trackRepository.findTracksByName("%" + name + "%");
	}
}
