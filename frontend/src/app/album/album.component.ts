import {Component, OnInit} from '@angular/core';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {NapsterServiceClient} from '../services/napster.service.client';
import {PersonServiceClient} from '../services/person.service.client';
import {CriticServiceClient} from '../services/critic.service.client';
import {AlbumServiceClient} from '../services/album.service.client';
import {Person} from '../models/Person';
import {Album} from '../models/Album';
import {Track} from '../models/Track';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent implements OnInit {

  albumId: String;
  person: Person;
  imageURL: any;
  albumsResult: Album;
  tracks: Track[];
  faEdit = faEdit;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private napsterService: NapsterServiceClient,
              private personService: PersonServiceClient,
              private criticService: CriticServiceClient,
              private albumService: AlbumServiceClient) {
    this.albumId = this.route.snapshot.paramMap.get('id');
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
      }, error => alert('could not load user'));
    this.albumService.findAlbumById(this.albumId.toString().split('.').pop())
      .subscribe(
        (response: Album) => {
          if (response) {
            if (response.napsterId) {
              this.fetchFromNapster('alb.' + response.napsterId);
            } else {
              this.albumsResult = response;
              this.tracks = response['tracks'];
              if (!this.albumsResult.imageURL) {
                this.imageURL = 'https://cloud.netlifyusercontent.com/assets/' +
                  '344dbf88-fdf9-42bb-adb4-46f01eedd629/514dc845-04f5-4538-8a4f-869b64243265/1-2.jpg';
              } else {
                this.imageURL = this.albumsResult.imageURL;
              }
            }
          } else {
            this.fetchFromNapster(this.albumId);
          }
        },
        error => alert('Our Server threw an error!'));
  }

  fetchFromNapster(albumId) {
    this.napsterService.findAlbumById(albumId)
      .subscribe(response => {
          this.albumsResult = response['albums'][0];
          this.napsterService.getAlbumTracks(this.albumsResult['links']['tracks']['href'])
            .subscribe(resp => {
              this.tracks = resp['tracks'];
            }, error => alert('Couldn\'t find tracks'));
          this.napsterService.findAlbumImagesById(albumId)
            .subscribe(resp => {
                this.imageURL = resp['images'][0].url;
              },
              error => alert('Server couldn\'t find an album art!'));
        },
        error => alert('Napster Server threw an error!'));
  }

  ngOnInit() {
  }

  goToTrackPage(tid) {
    this.router.navigate(['track' + '/' + tid]);
  }

}
