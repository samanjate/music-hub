package edu.neu.cs5200.orm.jpa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Album;
import edu.neu.cs5200.orm.jpa.entities.Critic;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.entities.Rating;
import edu.neu.cs5200.orm.jpa.entities.Review;
import edu.neu.cs5200.orm.jpa.entities.Track;
import edu.neu.cs5200.orm.jpa.repositories.AlbumRepository;
import edu.neu.cs5200.orm.jpa.repositories.CriticRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlaylistRepository;
import edu.neu.cs5200.orm.jpa.repositories.RatingRepository;
import edu.neu.cs5200.orm.jpa.repositories.ReviewRepository;
import edu.neu.cs5200.orm.jpa.repositories.TrackRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CriticService {
	
	@Autowired
	CriticRepository criticRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	TrackRepository trackRepository;
	
	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	AlbumRepository albumRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;
	
	Session sessionManager = Session.getInstance();
	
	@PostMapping("/api/critic")
	public Critic createCritic(@RequestBody Person person, HttpSession session) {
		Critic createdCritic = criticRepository.save(new Critic(person));
		if(sessionManager.checkSession() == null) {
			sessionManager.setSession(session, createdCritic);
		}
		return createdCritic;
	}
	
	@GetMapping("/api/critic")
	public List<Critic> findAllCritics() {
		return (List<Critic>) criticRepository.findAll();
	}
	
	@GetMapping("/api/critic/{cid}")
	public Critic findACriticById(@PathVariable("cid") int cid) {
		Optional<Critic> oCritic = criticRepository.findById(cid);
		if(oCritic.isPresent()) {
			return oCritic.get();
		} else {
			return null;
		}
	}
	
	@PutMapping("/api/critic/{cid}")
	public Critic updateCritic(@RequestBody Critic critic, @PathVariable("cid") int cid, HttpSession session) {
		Optional<Critic> oCritic = criticRepository.findById(cid);
		if(oCritic.isPresent()) {
			critic.setId(oCritic.get().getId());
			Critic updatedCritic = criticRepository.save(critic);
			if(sessionManager.checkSession() == null) {
				sessionManager.setSession(session, updatedCritic);
			}
			return updatedCritic;
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/api/critic/{cid}")
	public ResponseEntity<HttpStatus> deleteCriticById(@PathVariable("cid") int cid, HttpSession session) {
		Optional<Critic> oCritic = criticRepository.findById(cid);
		if(oCritic.isPresent()) {
			Critic critic = oCritic.get();
			List<Review> reviews = critic.getReviews();
			critic.setReviews(null);
			for(Review r : reviews) {
				reviewRepository.delete(r);
			}
			List<Rating> ratings = critic.getRatings();
			critic.setRatings(null);
			for(Rating r : ratings) {
				ratingRepository.delete(r);
			}
			for(Track t : critic.getLikes()) {
				t.getLikers().remove(critic);
				trackRepository.save(t);
			}
			critic.setLikes(null);
			for(Playlist p: critic.getPlaylists()) {
				playlistRepository.delete(p);
			}
			List<Person> people = critic.getFollowers();
			for(Person p : people) {
				p.getFollowing().remove(critic);
				personRepository.save(p);
			}
			criticRepository.delete(critic);
			if(sessionManager.checkSession().getId() == cid) {
				sessionManager.clearSession(session);
			}
			return ResponseEntity.ok(HttpStatus.OK);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/api/critic/like/{tid}")
	public ResponseEntity<HttpStatus> likeTrack(@RequestBody Track track, @PathVariable("tid") long tid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Track> oTrack = trackRepository.findById(track.getId());
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			List<Track> tracks = trackRepository.findTracksByNapsterId(tid);
			if(oCritic.isPresent()) {
				Critic critic = oCritic.get(); 
				if(oTrack.isPresent()) {
					for(Track t : critic.getLikes()) {
						if(t.getId() == oTrack.get().getId() || t.getNapsterId() == tid) {
							return ResponseEntity.ok(HttpStatus.ALREADY_REPORTED);
						}
					}
					oTrack.get().getLikers().add(critic);
					critic.getLikes().add(oTrack.get());
				} else if(!tracks.isEmpty()) {
					for(Critic c : tracks.get(0).getLikers()) {
						if(c.getId() == critic.getId()) {
							return ResponseEntity.ok(HttpStatus.ALREADY_REPORTED);
						}
					}
					tracks.get(0).getLikers().add(critic);
					critic.getLikes().add(tracks.get(0));
				} else {
					track.setNapsterId(tid);
					List<Critic> likers = new ArrayList<Critic>();
					likers.add(critic);
					track.setLikers(likers);
					critic.getLikes().add(track);
				}
				criticRepository.save(critic);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/critic/unlike/{tid}")
	public ResponseEntity<HttpStatus> unlikeTrack(@PathVariable("tid") long tid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Track> oTrack = trackRepository.findById((int) tid);
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			List<Track> tracks = trackRepository.findTracksByNapsterId(tid);
			if(oCritic.isPresent()) {
				Critic critic = oCritic.get(); 
				if(oTrack.isPresent()) {
					critic.getLikes().remove(oTrack.get());
					oTrack.get().getLikers().remove(critic);
				} 
				if(!tracks.isEmpty()) {
					critic.getLikes().remove(tracks.get(0));
					tracks.get(0).getLikers().remove(critic);
				}
				criticRepository.save(critic);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.ALREADY_REPORTED);
	}
	
	@GetMapping("/api/critic/like/{tid}") 
	public Boolean likeStatus(@PathVariable("tid") long tid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			if(oCritic.isPresent()) {
				for(Track t : oCritic.get().getLikes()) {
					if(t.getId() == (int) tid || t.getNapsterId() == tid) {
						return true;
					}
				}
				return false;
			}
		} else {
			sessionManager.clearSession(session);
		}
		return false;
	}
	
	@GetMapping("/api/critic/rate/{tid}") 
	public Rating rateStatus(@PathVariable("tid") long tid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			if(oCritic.isPresent()) {
				for(Rating r : oCritic.get().getRatings()) {
					if(r.getTrack().getId() == (int) tid 
							|| r.getTrack().getNapsterId() == tid) {
						return r;
					}
				}
			}
		} else {
			sessionManager.clearSession(session);
		}
		return null;
	}
	
	@PostMapping("/api/critic/rate/{rating}")
	public ResponseEntity<HttpStatus> createRating(@PathVariable("rating") int rating, @RequestBody Track track, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Track> oTrack = trackRepository.findById(track.getId());
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			List<Track> tracks = trackRepository.findTracksByNapsterId(track.getId());
			if(oCritic.isPresent()) {
				Critic critic = oCritic.get(); 
				Track t = null;
				if(oTrack.isPresent()) {
					t = oTrack.get();
				} else if(!tracks.isEmpty()) {
					t = tracks.get(0);
				} else {
					t = new Track();
					t.setNapsterId(track.getId());
					t.setName(track.getName());
					t.setPlaybackSeconds(track.getPlaybackSeconds());
					t.setPreviewURL(track.getPreviewURL());
					t.setArtist(null);
					trackRepository.save(t);
				}
				Rating newRatings = new Rating(critic, t, rating);
				ratingRepository.save(newRatings);
				return ResponseEntity.ok(HttpStatus.OK);
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/critic/rate/{rating}")
	public ResponseEntity<HttpStatus> updateRating(@PathVariable("rating") int rating, @RequestBody Track track, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			if(oCritic.isPresent()) {
				for(Rating r : oCritic.get().getRatings()) {
					if(r.getTrack().getId() == track.getId()
							|| r.getTrack().getNapsterId() == track.getId()) {
						r.setPoints(rating);
						ratingRepository.save(r);
						return ResponseEntity.ok(HttpStatus.OK);
					}
				}
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/api/critic/review")
	public ResponseEntity<HttpStatus> createReview(@RequestBody Album album, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Album> oAlbum = albumRepository.findById(album.getId());
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			List<Album> albums = albumRepository.findAlbumsByNapsterId(album.getId());
			if(oCritic.isPresent()) {
				Album alb = null;
				if(oAlbum.isPresent()) {
					alb = oAlbum.get();
				} else if(!albums.isEmpty()) {
					alb = albums.get(0);
				} else {
					alb = new Album();
					alb.setNapsterId(album.getId());
					alb.setName(album.getName());
					alb.setCopyright(album.getCopyright());
					alb.setAccountPartner(album.getAccountPartner());
					alb.setReleaseDate(album.getReleaseDate());
					alb.setAlbumLink(album.getAlbumLink());
					alb.setReviews(new ArrayList<Review>());
					albumRepository.save(alb);
				}
				reviewRepository.save(new Review(album.getReviews().get(0).getTitle(),
												album.getReviews().get(0).getText(),
												oCritic.get(),
												alb));
				return ResponseEntity.ok(HttpStatus.OK);
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/critic/review/{aid}") 
	public Review reviewStatus(@PathVariable("aid") long aid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			if(oCritic.isPresent()) {
				for(Review r : oCritic.get().getReviews()) {
					if(r.getAlbum().getId() == (int) aid 
							|| r.getAlbum().getNapsterId() == aid) {
						return r;
					}
				}
			}
		} else {
			sessionManager.clearSession(session);
		}
		return null;
	}
	
	@PutMapping("/api/critic/review")
	public ResponseEntity<HttpStatus> updateReview(@RequestBody Album album, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Album> oAlbum = albumRepository.findById(album.getId());
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			List<Album> albums = albumRepository.findAlbumsByNapsterId(album.getId());
			if(oCritic.isPresent()) {
				Album alb = null;
				if(oAlbum.isPresent()) {
					alb = oAlbum.get();
				} else if(!albums.isEmpty()) {
					alb = albums.get(0);
				} else {
					return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
				}
				for(Review r : oCritic.get().getReviews()) {
					if(r.getAlbum().getId() == alb.getId()) {
						r.setTitle(album.getReviews().get(0).getTitle());
						r.setText(album.getReviews().get(0).getText());
						reviewRepository.save(r);
						return ResponseEntity.ok(HttpStatus.OK);
					}
				}
				return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
			}
		} else {
			sessionManager.clearSession(session);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/critic/review/{rid}")
	public ResponseEntity<HttpStatus> deleteReview(@PathVariable("rid") int rid, HttpSession session) {
		Person person = sessionManager.checkSession();
		if(person != null) {
			Optional<Critic> oCritic = criticRepository.findById(person.getId());
			if(oCritic.isPresent()) {
				Optional<Review> oReview = reviewRepository.findById(rid);
				if(oReview.isPresent()) {
					reviewRepository.delete(oReview.get());
					return ResponseEntity.ok(HttpStatus.OK);
				}
				return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
			}
		}
		sessionManager.clearSession(session);
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}

}
