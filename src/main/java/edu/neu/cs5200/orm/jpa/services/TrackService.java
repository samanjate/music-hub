package edu.neu.cs5200.orm.jpa.services;

import java.util.ArrayList;
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
import edu.neu.cs5200.orm.jpa.entities.Track;
import edu.neu.cs5200.orm.jpa.repositories.AlbumRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;
import edu.neu.cs5200.orm.jpa.repositories.TrackRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrackService {

	@Autowired
	TrackRepository trackRepository;
	
	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	AlbumRepository albumRepository;
	
	Session sessionManager = Session.getInstance();

	@PostMapping("/create/track")
	public ResponseEntity<String> createTrack(@RequestParam("file") MultipartFile file,
			@RequestParam("track") String track, @RequestParam("album") String album) {
		String message = "";
		try {
			int loggedInUserId = sessionManager.checkSession().getId();
			ObjectMapper mapper = new ObjectMapper();
			Track objTrack = mapper.readValue(track, Track.class);
			Album objAlbum = mapper.readValue(album, Album.class);
			Artist createdByArtist = artistRepository.findById(loggedInUserId).get();
			Optional<Album> optionalAlbum = albumRepository.findById(objAlbum.getId());
			if(optionalAlbum.isPresent()) {
				objAlbum = optionalAlbum.get();
			} else {
				objAlbum = albumRepository.save(objAlbum);
			}
			List<Album> albumsTrackPresent = new ArrayList<>(); 
			albumsTrackPresent.add(objAlbum);
			objTrack.setAlbums(albumsTrackPresent);
			objTrack.setArtist(createdByArtist);
			Track createdTrack = trackRepository.save(objTrack);
			
			//once the track is created, save the track in album too
			List<Track> newTracksList;
			if(objAlbum.getTracks()==null) {
				newTracksList = new ArrayList<>();
				newTracksList.add(createdTrack);
			}else {
				newTracksList = objAlbum.getTracks();
				newTracksList.add(createdTrack);	
			}
			objAlbum.setArtist(createdByArtist);
			objAlbum.setTracks(newTracksList);
			albumRepository.save(objAlbum);
			
			S3Connection.getInstance()
					.uploadFilesInFolder( loggedInUserId + "/" + createdTrack.getId(), file);
			
			createdTrack.setPreviewURL(S3Connection.AWS_BUCKET_URL + loggedInUserId + "/" + createdTrack.getId()+"/" + file.getOriginalFilename());
			trackRepository.save(createdTrack);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	
	@GetMapping("/api/track/name/{name}")
	public List<Track> findTracksByAlbumName(@PathVariable("name") String name) {
		return trackRepository.findTracksByName("%"+name+"%");
	}
	
	
	@GetMapping("/api/track/id/{tid}")
	public Track findTrackById(@PathVariable("tid") int id) {
		Optional<Track> oTrack = trackRepository.findById(id);
		if(oTrack.isPresent()) {
			return oTrack.get();
		}
		return null;
	}

	@GetMapping("/api/track/{name}")
	public List<Track> findTracksByName(@PathVariable("name") String name) {
		return trackRepository.findTracksByName("%" + name + "%");
	}
	
	@GetMapping("/api/track/artist/{artistId}")
	public List<Track> findTracksByArtistId(@PathVariable("artistId") int artistId) {
		Artist a = new Artist();
		a.setId(artistId);
		return trackRepository.findTracksByArtistId(a);
	}
}
