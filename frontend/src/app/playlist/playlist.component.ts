import {Component, OnInit} from '@angular/core';
import {PlaylistServiceClient} from '../services/playlist.service.client';
import {Playlist} from '../models/Playlist';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css', '../../app/album/album.component.css']
})
export class PlaylistComponent implements OnInit {
  playlist: Playlist;
  playlistId;

  constructor(private route: ActivatedRoute, private router: Router, private playlistService: PlaylistServiceClient) {
  }

  ngOnInit() {
    this.playlistId = this.route.snapshot.paramMap.get('id');
    this.playlistService.findPlaylistById(this.playlistId)
      .subscribe((response: Playlist) => {
        this.playlist = response;
        console.log(this.playlist);
      });
  }

  goToTrackPage(tid) {
    this.router.navigate(['track' + '/' + tid]);
  }

}
