import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class TrackServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  TRACK_URL = this.DOMAIN_URL + '/api/track/';

  constructor(private http: HttpClient) {
  }

  findTracksByArtistId(artistId) {
    return this.http.get(this.TRACK_URL + 'artist/' + artistId);
  }

  findTrackById(trackId) {
    return this.http.get(this.TRACK_URL + 'id/' + trackId);
  }

  findTrackByName(name) {
    return this.http.get(this.TRACK_URL + 'name/' + name);
  }

  findAllTracks() {
    return this.http.get(this.TRACK_URL);
  }

  deleteTrackById(id) {
    return this.http.delete(this.TRACK_URL + id);
  }
}
