import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NapsterServiceClient} from '../services/napster.service.client';
import {Track} from '../models/Track';
import {Playlist} from '../models/Playlist';
import {Album} from '../models/Album';
import {Artist} from '../models/Artist';
import {Person} from '../models/Person';
import {PersonServiceClient} from '../services/person.service.client';
import {TrackServiceClient} from '../services/track.service.client';
import {AlbumServiceClient} from '../services/album.service.client';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css', '../../assets/css/style.css']
})

export class SearchResultsComponent implements OnInit {

  searchQuery;
  tracksResultList: Track[] = [];
  playlistsResultList: Playlist[] = [];
  albumsResultList: Album[] = [];
  artistsResultList: Artist[] = [];
  peopleResultList: Person[] = [];

  constructor(private route: ActivatedRoute, private napsterService: NapsterServiceClient,
              private personService: PersonServiceClient, private trackService: TrackServiceClient,
              private albumService: AlbumServiceClient) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.searchQuery = params['query'];
        this.personService.findUserByUsername(this.searchQuery)
          .subscribe((response: Person[]) => {
            this.peopleResultList = response;
          });
        this.napsterService.findAllDomainObjectsByName(this.searchQuery)
          .subscribe((response) => {
            if (response['search'].data.tracks) {
              response['search'].data.tracks.map(r => {
                this.tracksResultList.push(r);
              });
            }
            this.playlistsResultList = response['search'].data.playlists;
            if (response['search'].data.albums) {
              response['search'].data.albums.map(r => {
                this.albumsResultList.push(r);
              });
            }
            this.artistsResultList = response['search'].data.artists;
          });

        this.trackService.findTrackByName(this.searchQuery)
          .subscribe((response: Track[]) => {
            if (response) {
              response.map(r => {
                this.tracksResultList.push(r);
              });
            }
          });

        this.albumService.findAlbumByName(this.searchQuery)
          .subscribe((response: Album[]) => {
            if (response) {
              response.map(r => {
                this.albumsResultList.push(r);
              });
            }
          });
      });
  }
}
