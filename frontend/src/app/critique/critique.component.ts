import {Component, Input, OnInit} from '@angular/core';
import {PersonServiceClient} from '../services/person.service.client';
import {Person} from '../models/Person';
import {CriticServiceClient} from '../services/critic.service.client';
import {Router} from '@angular/router';
import {TrackServiceClient} from '../services/track.service.client';
import {AlbumServiceClient} from '../services/album.service.client';
import {Track} from '../models/Track';
import {Album} from '../models/Album';

@Component({
  selector: 'app-critique',
  templateUrl: './critique.component.html',
  styleUrls: ['./critique.component.css']
})
export class CritiqueComponent implements OnInit {

  @Input() domainObject: string;
  person: Person;
  likes: any;
  reviews: any;
  ratings: any;

  constructor(private router: Router,
              private personService: PersonServiceClient,
              private criticService: CriticServiceClient,
              private trackService: TrackServiceClient,
              private albumService: AlbumServiceClient) {
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
        if (this.domainObject === 'like') {
          this.criticService.getLikes(this.person.id)
            .subscribe(response => {
              if (response) {
                this.likes = response;
              }
            }, () => alert('Couldn\'t load likes!'));
        } else if (this.domainObject === 'rating') {
          this.criticService.getRatings(this.person.id)
            .subscribe((response: any) => {
              if (response) {
                this.ratings = response;
                response.map(rating =>  {
                  this.trackService.findAllTracks()
                    .subscribe((tracks: any) => {
                      tracks.map(track => {
                        track.ratings.map(r => {
                          if (r.id === rating['id']) {
                            rating['track'] = track;
                          }
                        });
                      });
                    }, () => alert('Some error occurred!'));
                });
              }
            }, () => alert('Couldn\'t load ratings!'));
        } else if (this.domainObject === 'review') {
          this.criticService.getReviews(this.person.id)
            .subscribe((response: any) => {
              if (response) {
                this.reviews = response;
                response.map(review =>  {
                  this.albumService.findAllAlbums()
                    .subscribe((albums: any) => {
                      albums.map(album => {
                        album.reviews.map(r => {
                          if (r.id === review['id']) {
                            review['album'] = album;
                          }
                        });
                      });
                      }, () => alert('Some error occurred!'));
                });
              }
            }, () => alert('Couldn\'t load reviews!'));
        }
      }, () => alert('could not load user'));
  }

  ngOnInit() {
  }

  goToTrackPage(track) {
    if (track.napsterId) {
      this.router.navigate(['track/tra.' + track.napsterId]);
    } else {
      this.router.navigate(['track/' + track.id]);
    }
  }

  goToAlbumPage(album) {
    if (album.napsterId) {
      this.router.navigate(['album/alb.' + album.napsterId]);
    } else {
      this.router.navigate(['album/' + album.id]);
    }
  }
}
