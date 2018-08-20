import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ArtistServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  ARTIST_URL = this.DOMAIN_URL + '/api/artist';

  constructor(private http: HttpClient) {
  }

  findAllArtists() {
    return this.http.get(this.ARTIST_URL);
  }

  createArtist(artist) {
    return this.http.post(this.ARTIST_URL, artist);
  }

  updateArtist(artist) {
    return this.http.put(this.ARTIST_URL + '/' + artist.id, artist);
  }

  deleteArtist(id) {
    return this.http.delete(this.ARTIST_URL + '/' + id);
  }

  findArtistById(aid) {
    return this.http.get(this.ARTIST_URL + '/' + aid);
  }
}
