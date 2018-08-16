package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Artist;
import edu.neu.cs5200.orm.jpa.entities.Track;

public interface TrackRepository extends CrudRepository<Track, Integer> {
	@Query("SELECT t FROM Track t WHERE t.name = :name")
	public List<Track> findTracksByName(@Param("name") String name);

	@Query("SELECT t FROM Track t WHERE t.artist = :artistId")
	public List<Track> findTracksByArtistId(@Param("artistId") Artist artist);
}
