import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class PlaylistServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  PLAYLIST_URL = this.DOMAIN_URL + '/api/playlist/';

  constructor(private http: HttpClient) {
  }

  findPlaylistsByPersonId(personId) {
    return this.http.get(this.PLAYLIST_URL + 'person/' + personId);
  }

  findPlaylistById(personId) {
    return this.http.get(this.PLAYLIST_URL + personId);
  }

}
