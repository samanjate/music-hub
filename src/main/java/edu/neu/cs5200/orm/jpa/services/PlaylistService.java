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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.neu.cs5200.aws.S3Connection;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.entities.Track;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlaylistRepository;
import edu.neu.cs5200.orm.jpa.repositories.TrackRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlaylistService {

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	TrackRepository trackRepository;

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
			S3Connection.getInstance().uploadFilesInFolder(loggedInUserId + "/" + createdPlaylist.getId(), file);

			createdPlaylist.setImageURL(S3Connection.AWS_BUCKET_URL + loggedInUserId + "/" + createdPlaylist.getId()
					+ "/" + file.getOriginalFilename());
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

	@PostMapping("/api/create/track/for/playlist/{playlistId}")
	public Playlist addTrackForPlaylist(@PathVariable("playlistId") int playlistId, @RequestBody Track track) {
		Optional<Playlist> optionalP = playlistRepository.findById(playlistId);
		if (optionalP.isPresent()) {
			Playlist p = optionalP.get();
			p.getTracks().add(track);
			return playlistRepository.save(p);
		}

		return null;
	}

	@PostMapping("/api/create/napster/track/for/playlist/{playlistId}")
	public Playlist addNapsterTrackForPlaylist(@PathVariable("playlistId") int playlistId, @RequestBody Track track) {
		Optional<Playlist> optionalP = playlistRepository.findById(playlistId);
		List<Track> tracks = trackRepository.findTracksByNapsterId(track.getNapsterId());
		if (tracks.size() == 1) {
			Playlist p = optionalP.get();
			p.getTracks().add(tracks.get(0));
			return playlistRepository.save(p);
		} else {
			trackRepository.save(track);
			Playlist p = optionalP.get();
			p.getTracks().add(track);
			return playlistRepository.save(p);
		}
	}

	@GetMapping("/api/playlist/{id}")
	public Playlist findPlaylistById(@PathVariable("id") int id) {
		Optional<Playlist> p = playlistRepository.findById(id);
		if (p.isPresent())
			return p.get();
		else
			return null;
	}
}
