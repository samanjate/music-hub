import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Track} from '../models/Track';
import {Playlist} from '../models/Playlist';
import {Album} from '../models/Album';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) {
  }

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';

  pushTrackToStorage(file: File, track: Track, album: Album): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('track', JSON.stringify(track));
    formdata.append('album', JSON.stringify(album));

    const req = new HttpRequest('POST', this.DOMAIN_URL + '/create/track', formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }

  pushPlaylistToStorage(file: File, playlist): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('playlist', JSON.stringify(playlist));

    const req = new HttpRequest('POST', this.DOMAIN_URL + '/create/playlist', formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }

  pushAlbumToStorage(file: File, album): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('album', JSON.stringify(album));

    const req = new HttpRequest('POST', this.DOMAIN_URL + '/create/album', formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get('/getallfiles');
  }
}
