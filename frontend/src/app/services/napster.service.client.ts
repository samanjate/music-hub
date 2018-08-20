import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class NapsterServiceClient {

  NAPSTER_KEY = 'YWJkMTA2MTMtN2I5Zi00MDE1LWE1MGQtMzJjNTZhOTFlMzBh';
  NAPSTER_SEARCH_PREFIX = 'http://api.napster.com/v2.2';
  NAPSTER_SEARCH_QUERY_URL = this.NAPSTER_SEARCH_PREFIX + '/search?apikey=' + this.NAPSTER_KEY + '&query=';

  constructor(private http: HttpClient) {
  }

  findAllDomainObjectsByName(searchString) {
    return this.http.get(this.NAPSTER_SEARCH_QUERY_URL + searchString);
  }

  findTrackByName(searchString) {
    return this.http.get(this.NAPSTER_SEARCH_QUERY_URL + searchString + '&type=track');
  }

  findPlaylistByName(searchString) {
    return this.http.get(this.NAPSTER_SEARCH_QUERY_URL + searchString + '&type=playlist');
  }

  findAlbumByName(searchString) {
    return this.http.get(this.NAPSTER_SEARCH_QUERY_URL + searchString + '&type=album');
  }

  findArtistByName(searchString) {
    return this.http.get(this.NAPSTER_SEARCH_QUERY_URL + searchString + '&type=artist');
  }

  findTrackById(id) {
    return this.http.get(this.NAPSTER_SEARCH_PREFIX + '/tracks/' + id + '?apikey=' + this.NAPSTER_KEY);
  }

  findAlbumById(id) {
    return this.http.get(this.NAPSTER_SEARCH_PREFIX + '/albums/' + id + '?apikey=' + this.NAPSTER_KEY);
  }

  findAlbumImagesById(id) {
    return this.http.get(this.NAPSTER_SEARCH_PREFIX + '/albums/' + id + '/images' + '?apikey=' + this.NAPSTER_KEY);
  }

  getAlbumTracks(url) {
    return this.http.get(url + '?apikey=' + this.NAPSTER_KEY);
  }

  findArtistById(id) {
    return this.http.get(this.NAPSTER_SEARCH_PREFIX + '/artists/' + id + '?apikey=' + this.NAPSTER_KEY);
  }

  getArtistImage(url) {
    return this.http.get(url + '?apikey=' + this.NAPSTER_KEY);
  }

  getArtistAlbums(url) {
    return this.http.get(url + '?apikey=' + this.NAPSTER_KEY);
  }
}
