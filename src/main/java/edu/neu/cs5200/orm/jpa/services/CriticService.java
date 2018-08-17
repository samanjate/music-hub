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

import edu.neu.cs5200.orm.jpa.entities.Critic;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Track;
import edu.neu.cs5200.orm.jpa.repositories.CriticRepository;
import edu.neu.cs5200.orm.jpa.repositories.TrackRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CriticService {
	
	@Autowired
	CriticRepository criticRepository;
	
	@Autowired
	TrackRepository trackRepository;
	
	Session sessionManager = Session.getInstance();
	
	@PostMapping("/api/critic")
	public Critic createCritic(@RequestBody Person person, HttpSession session) {
		Critic createdCritic = criticRepository.save(new Critic(person));
		sessionManager.setSession(session, createdCritic);
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
			sessionManager.setSession(session, updatedCritic);
			return updatedCritic;
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/api/critic/{cid}")
	public void deleteCriticById(@PathVariable("cid") int cid, @RequestBody Critic critic, HttpSession session) {
		criticRepository.deleteById(cid);
		sessionManager.clearSession(session);
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

}
