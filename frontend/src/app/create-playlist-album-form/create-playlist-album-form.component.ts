import {Component, Input, OnInit} from '@angular/core';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {UploadFileService} from '../upload/upload-file.service';
import {Track} from '../models/Track';
import {Playlist} from '../models/Playlist';
import {Album} from '../models/Album';

@Component({
  selector: 'app-create-playlist-album-form',
  templateUrl: './create-playlist-album-form.component.html',
  styleUrls: ['./create-playlist-album-form.component.css']
})
export class CreatePlaylistAlbumFormComponent implements OnInit {

  @Input() domainObject;

  playlistOrAlbum;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = {percentage: 0};
  successMsg;
  constructor(private uploadService: UploadFileService) {
  }

  ngOnInit() {
    if (this.domainObject === 'playlist') {
      this.playlistOrAlbum = new Playlist();
    } else {
      this.playlistOrAlbum = new Album();
    }
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;
    this.currentFileUpload = this.selectedFiles.item(0);

    if (this.domainObject === 'playlist') {
      this.uploadService.pushPlaylistToStorage(this.currentFileUpload, this.playlistOrAlbum)
        .subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.successMsg = 'Playlist successfully created. Please close this wizard!';
          }
        });
    } else {
      this.uploadService.pushAlbumToStorage(this.currentFileUpload, this.playlistOrAlbum)
        .subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.successMsg = 'Album successfully created. Please close this wizard!';
          }
        });
    }
    this.selectedFiles = undefined;
  }

}
