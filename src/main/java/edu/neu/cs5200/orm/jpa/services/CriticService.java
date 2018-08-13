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

import edu.neu.cs5200.orm.jpa.entities.Critic;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.CriticRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CriticService {
	
	@Autowired
	CriticRepository criticRepository;
	
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
	public Critic updateCritic(@RequestBody Critic critic, HttpSession session) {
		Critic updatedCritic = criticRepository.save(critic);
		sessionManager.setSession(session, updatedCritic);
		return updatedCritic;
	}
	
	@DeleteMapping("/api/critic/{cid}")
	public void deleteCriticById(@RequestBody Critic critic, @PathVariable("cid") int cid, HttpSession session) {
		criticRepository.deleteById(cid);
		sessionManager.clearSession(session);
	}

}
