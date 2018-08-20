import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class PersonServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  REGISTER_URL = this.DOMAIN_URL + '/api/register';
  USER_URL = this.DOMAIN_URL + '/api/login';
  LOGIN_URL = this.DOMAIN_URL + '/api/login';
  LOGOUT_URL = this.DOMAIN_URL + '/api/logout';
  SESSION_URL = this.DOMAIN_URL + '/api/get/session';

  TRACK_UNDER_PLAYLIST = this.DOMAIN_URL + '/api/create/track/for/playlist/PLAYLIST_ID';
  NAPSTER_TRACK_UNDER_PLAYLIST = this.DOMAIN_URL + '/api/create/napster/track/for/playlist/PLAYLIST_ID';

  ARTIST_URL = this.DOMAIN_URL + '/api/artist';

  constructor(private http: HttpClient) {
  }

  findAllPersons() {
    return this.http.get(this.USER_URL);
  }

  login(username, password) {
    return this.http.get(this.LOGIN_URL + '?username=' + username + '&password=' + password);
  }

  logout() {
    return this.http.get(this.LOGOUT_URL);
  }

  checkSession() {
    return this.http.get(this.SESSION_URL);
  }

  findUserByUsername(username) {
    return this.http.get(this.DOMAIN_URL + '/api/person/username/' + username);
  }


  findUserFollowing(id) {
    return this.http.get(this.DOMAIN_URL + '/api/person/following/' + id);
  }


  findUserById(id) {
    return this.http.get(this.USER_URL + '/' + id);
  }

  addTrackToPlaylistForPerson(playlistId, track) {
    const createPlaylistForPerson = this.TRACK_UNDER_PLAYLIST
      .replace('PLAYLIST_ID', playlistId);
    return this.http.post(createPlaylistForPerson, track);
  }

  addNapsterTrackToPlaylistForPerson(playlistId, track) {
    track['id'] = 0;
    const createPlaylistForPerson = this.NAPSTER_TRACK_UNDER_PLAYLIST
      .replace('PLAYLIST_ID', playlistId);
    return this.http.post(createPlaylistForPerson, track);
  }

  followPerson(currentPersonId, anotherPersonId) {
    return this.http.post(this.DOMAIN_URL + '/api/follow/' + currentPersonId, anotherPersonId);
  }

  unfollowPerson(currentPersonId, anotherPersonId) {
    return this.http.post(this.DOMAIN_URL + '/api/unfollow/' + currentPersonId, anotherPersonId);
  }

  /**
   * @deprecated
   */
  createPerson(person) {
    return this.http.post(this.REGISTER_URL + '/' + person.userType, person);
  }

  /**
   * @deprecated
   */
  update(person) {
    if (person.dType === 'ARTIST') {
      return this.http.put(this.ARTIST_URL + '/' + person.id, person);
    }
  }

// findUserByUsername(username, password) {
//   return fetch(this.USER_URL)
//     .then(response => response.json(),
//       response => alert('Error thrown by server'));
// }
//
// deleteUser(user) {
//   return fetch(this.USER_URL)
//     .then(response => response.json(),
//       response => alert('Error thrown by server'));
// }
}
