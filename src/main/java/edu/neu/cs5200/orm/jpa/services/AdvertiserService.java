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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Advertisement;
import edu.neu.cs5200.orm.jpa.entities.Advertiser;
import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.repositories.AdvertisementRepository;
import edu.neu.cs5200.orm.jpa.repositories.AdvertiserRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlaylistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdvertiserService {

	@Autowired
	AdvertiserRepository advertiserRepository;
	
	@Autowired
	PersonRepository personRepository;

	@Autowired
	AdvertisementRepository advertisementRepository;

	@Autowired
	ArtistRepository artistRepository;
	
	@Autowired
	PlaylistRepository playlistRepository;

	Session sessionManager = Session.getInstance();

	@PostMapping("/api/advertiser")
	public Advertiser createAdvertiser(@RequestBody Person person, HttpSession session) {
		Advertiser createdAdvertiser = advertiserRepository.save(new Advertiser(person));
		if(sessionManager.checkSession() == null) {
			sessionManager.setSession(session, createdAdvertiser);
		}
		return createdAdvertiser;
	}
	
	@GetMapping("/api/advertiser")
	public List<Advertiser> getAllAdvertisers() {
		return (List<Advertiser>) advertiserRepository.findAll();
	}
	
	@PutMapping("/api/advertiser/{aid}")
	public Advertiser updateAdvertiser(@RequestBody Advertiser advertiser, @PathVariable("aid") int aid, HttpSession session) {
		Optional<Advertiser> oAdvertiser = advertiserRepository.findById(aid);
		if(oAdvertiser.isPresent()) {
			advertiser.setId(aid);
			Advertiser updatedAdvertiser = advertiserRepository.save(advertiser);
			if(sessionManager.checkSession() == null) {
				sessionManager.setSession(session, updatedAdvertiser);
			}
			return updatedAdvertiser;
		} else {
			return null;
		}
	}
	
	@DeleteMapping("/api/advertiser/{aid}")
	public ResponseEntity<HttpStatus> deleteAdvertiser(@PathVariable("aid") int aid, HttpSession session) {
		Optional<Advertiser> oAdvertiser = advertiserRepository.findById(aid);
		if(oAdvertiser.isPresent()) {
			Advertiser advertiser = oAdvertiser.get();
			for(Playlist p: advertiser.getPlaylists()) {
				playlistRepository.delete(p);
			}
			for(Advertisement ad : advertiser.getAds()) {
				advertisementRepository.delete(ad);
			}
			List<Person> people = advertiser.getFollowers();
			for(Person p : people) {
				p.getFollowing().remove(advertiser);
				personRepository.save(p);
			}
			advertiserRepository.delete(advertiser);
			if(sessionManager.checkSession().getId() == aid) {
				sessionManager.clearSession(session);
			}
			return ResponseEntity.ok(HttpStatus.OK);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/api/advertiser/create/advertisement/napster")
	public Advertisement createAdvertisementForNapsterArtist(@RequestBody Advertisement ad,
			@RequestParam("artist") long artistId, @RequestParam("advertiser") int advertiserId, HttpSession session) {
		Advertiser advertiser = advertiserRepository.findById(advertiserId).get();
		ad.setAdvertiser(advertiser);
		Artist a = artistRepository.findArtistByNapsterId(artistId);

		List<Advertisement> ads;

		if (a == null) {
			a = new Artist();
			a.setNapsterId(artistId);
			a = artistRepository.save(a);
			ads = new ArrayList<>();
		} else {
			ads = a.getAdvertisements();
		}
		ad.setCreatedInArtistPage(a);

		ads.add(ad);
		a.setAdvertisements(ads);
		ad.setCreatedInArtistPage(a);

		return advertisementRepository.save(ad);
	}

	@GetMapping("/api/advertisement/advertiser/{advertiserId}")
	public List<Advertisement> findAdvertisementsByNapsterArtist(
			@PathVariable("advertiserId") int advertiserId) {
		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		return advertisementRepository.findAdvertisementsByAdvertiser(advertiser);
	}
	
	@GetMapping("/api/advertisement/{napsterArtistId}/in/napster/artist/{advertiserId}")
	public List<Advertisement> findAdvertisementsByNapsterArtist(@PathVariable("napsterArtistId") long napsterArtistId,
			@PathVariable("advertiserId") int advertiserId) {
		Artist a = artistRepository.findArtistByNapsterId(napsterArtistId);
		a.setNapsterId(napsterArtistId);
		Advertiser advertiser = new Advertiser();
		advertiser.setId(advertiserId);
		return advertisementRepository.findAdvertisementsByNapsterArtistAndAdvertiser(a, advertiser);
	}
}
