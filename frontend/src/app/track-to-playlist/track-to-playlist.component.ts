import {Component, Input, OnInit} from '@angular/core';
import {PlaylistServiceClient} from '../services/playlist.service.client';
import {Playlist} from '../models/Playlist';
import {Person} from '../models/Person';
import {PersonServiceClient} from '../services/person.service.client';
import {Album} from '../models/Album';

@Component({
  selector: 'app-track-to-playlist',
  templateUrl: './track-to-playlist.component.html',
  styleUrls: ['./track-to-playlist.component.css']
})
export class TrackToPlaylistComponent implements OnInit {

  @Input() trackToAdd;

  playlists: Playlist[];
  albums: Album[];
  person: Person;

  constructor(private personService: PersonServiceClient, private playlistService: PlaylistServiceClient) {
  }

  ngOnInit() {
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
        this.playlistService.findPlaylistsByPersonId(this.person.id)
          .subscribe((playlists: Playlist[]) => {
            this.playlists = playlists;
          });
        if (person.dType === 'ARTIST') {
          this.albums = this.person.albums;
        }
      });
  }

  addTrackToPlaylist(playlistId) {
    if (typeof this.trackToAdd.id === 'string' && this.trackToAdd.id.startsWith('tra')) {
      this.trackToAdd['napsterId'] = Number(this.trackToAdd.id.substring(4));
      this.personService.addNapsterTrackToPlaylistForPerson(playlistId, this.trackToAdd)
        .subscribe(response => {
        });
    } else {
      this.personService.addTrackToPlaylistForPerson(playlistId, this.trackToAdd)
        .subscribe(response => {

        });
    }
  }

  addTrackToAlbum(albumId) {
  }

}
