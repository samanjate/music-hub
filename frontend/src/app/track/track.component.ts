import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NapsterServiceClient} from '../services/napster.service.client';
import {Track} from '../models/Track';
import {faThumbsUp, faStar} from '@fortawesome/free-solid-svg-icons';
import {PersonServiceClient} from '../services/person.service.client';
import {TrackServiceClient} from '../services/track.service.client';
import {Person} from '../models/Person';
import {CriticServiceClient} from '../services/critic.service.client';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit {

  person: Person;
  trackId: String;
  imageURL: any;
  previewURL: any;
  tracksResult: Track;
  faThumbsUp = faThumbsUp;
  faStar = faStar;
  like: any;

  constructor(private route: ActivatedRoute,
              private napsterService: NapsterServiceClient,
              private personService: PersonServiceClient,
              private criticService: CriticServiceClient,
              private trackService: TrackServiceClient) {
    this.trackId = this.route.snapshot.paramMap.get('id');
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
        this.criticService.likeStatus(this.trackId.split('.').pop())
          .subscribe(resp => {
              this.like = resp;
            },
            err => {
              alert('Couldn\'t determine like');
            });
      }
      , error => alert('could not load user'));
    this.trackService.findTrackById(this.trackId.split('.').pop())
      .subscribe(
        (response: Track) => {
          if (response) {
            if (response['napsterId']) {
              this.fetchFromNapster('tra.' + response['napsterId']);
            } else {
              this.tracksResult = response;
              this.previewURL = response.previewURL;
              this.imageURL = 'https://cloud.netlifyusercontent.com/assets/' +
                '344dbf88-fdf9-42bb-adb4-46f01eedd629/514dc845-04f5-4538-8a4f-869b64243265/1-2.jpg';
            }
          } else {
            this.fetchFromNapster(this.trackId);
          }
        },
        error => alert('Our Server threw an error!'));
  }

  ngOnInit() {
  }

  fetchFromNapster(trackId) {
    this.napsterService.findTrackById(trackId)
      .subscribe(response => {
          this.tracksResult = response['tracks'][0];
          this.previewURL = response['tracks'][0].previewURL;
          this.napsterService.findAlbumImagesById(response['tracks'][0].albumId)
            .subscribe(resp => {
                this.imageURL = resp['images'][0].url;
              },
              error => alert('Server couldn\'t find an album art!'));
        },
        error => alert('Napster Server threw an error!'));
  }

  likeTrack() {
    this.tracksResult.id = parseInt(this.trackId.split('.').pop());
    this.criticService.likeTrack(this.trackId.split('.').pop(), this.tracksResult)
      .subscribe(resp => {
          this.like = true;
        },
        err => {
          alert('Couldn\'t like Item');
        });
  }

  unlikeTrack() {
    this.criticService.unlikeTrack(this.trackId.split('.').pop())
      .subscribe(resp => {
          this.like = false;
        },
        err => {
          alert('Couldn\'t unlike Item');
        });
  }

}
