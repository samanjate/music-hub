package neu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import neu.northeastern.cs5200.models.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {

}
