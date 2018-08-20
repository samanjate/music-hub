import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class AlbumServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  ALBUM_URL = this.DOMAIN_URL + '/api/album/';

  constructor(private http: HttpClient) {
  }

  findAlbumsByArtistId(artistId) {
    return this.http.get(this.ALBUM_URL + 'artist/' + artistId);
  }

  findAlbumById(aid) {
    return this.http.get(this.ALBUM_URL + aid);
  }

  findAlbumByName(name) {
    return this.http.get(this.ALBUM_URL + 'name/' + name);
  }

  findAllAlbums() {
    return this.http.get(this.ALBUM_URL);
  }

  deleteAlbumById(id) {
    return this.http.delete(this.ALBUM_URL + id);
  }
}
