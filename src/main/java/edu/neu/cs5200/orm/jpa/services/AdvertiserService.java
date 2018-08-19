package edu.neu.cs5200.orm.jpa.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.entities.Advertisement;
import edu.neu.cs5200.orm.jpa.entities.Advertiser;
import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.AdvertisementRepository;
import edu.neu.cs5200.orm.jpa.repositories.AdvertiserRepository;
import edu.neu.cs5200.orm.jpa.repositories.ArtistRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdvertiserService {

	@Autowired
	AdvertiserRepository advertiserRepository;

	@Autowired
	AdvertisementRepository advertisementRepository;

	@Autowired
	ArtistRepository artistRepository;

	Session sessionManager = Session.getInstance();

	@PostMapping("/api/advertiser")
	public Advertiser createAdvertiser(@RequestBody Person person, HttpSession session) {
		Advertiser createdAdvertiser = advertiserRepository.save(new Advertiser(person));
		sessionManager.setSession(session, createdAdvertiser);
		return createdAdvertiser;
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
