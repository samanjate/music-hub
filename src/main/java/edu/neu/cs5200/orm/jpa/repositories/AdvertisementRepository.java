package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Advertisement;
import edu.neu.cs5200.orm.jpa.entities.Advertiser;
import edu.neu.cs5200.orm.jpa.entities.Artist;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer> {

	@Query("SELECT a FROM Advertisement a WHERE a.createdInArtistPage = :a and a.advertiser = :advertiser")
	List<Advertisement> findAdvertisementsByNapsterArtistAndAdvertiser(@Param("a") Artist a,
			@Param("advertiser") Advertiser advertiser);

}
