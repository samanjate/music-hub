import {Component, Input, OnInit} from '@angular/core';
import {UploadFileService} from '../upload/upload-file.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {Track} from '../models/Track';
import {Album} from '../models/Album';
import {AlbumServiceClient} from '../services/album.service.client';
import {ChangeDetectorRef} from '@angular/core';

@Component({
  selector: 'app-create-track-form',
  templateUrl: './create-track-form.component.html',
  styleUrls: ['./create-track-form.component.css', '../../assets/css/style.css']
})
export class CreateTrackFormComponent implements OnInit {

  @Input() loggedInUser;
  track: Track;
  allAlbums: Album[];
  allAlbumsSafe: Album[];
  selectedFiles: FileList;
  currentFileUpload: File;
  addToAlbum: Album;
  progress: { percentage: number } = {percentage: 0};
  query: string;
  successMsg;

  constructor(private uploadService: UploadFileService, private albumService: AlbumServiceClient, private cd: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.track = new Track();
    this.searchForAlbums();
  }

  onChange(event) {
    this.searchForAlbums();
  }

  searchForAlbums() {
    if (this.loggedInUser) {
      this.albumService.findAlbumsByArtistId(this.loggedInUser.id)
        .subscribe((response: Album[]) => {
          this.allAlbums = response;
          this.allAlbumsSafe = response;
        });
    }
  }

  filterAlbumName(q) {
    this.allAlbums = this.allAlbumsSafe;
    this.allAlbums = this.filterValue(q);
    this.cd.detectChanges();
  }

  filterValue(q) {
    return this.allAlbums.filter(
      currAlbum => {
        if (currAlbum.name.indexOf(q) >= 0) {
          return currAlbum.name;
        }
      });
  }

  loadInputAlbumName(albumDet) {
    this.addToAlbum = albumDet;
    this.query = albumDet.name;
    this.allAlbums = [];
    this.cd.detectChanges();
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;
    if (!this.addToAlbum || this.addToAlbum.name !== this.query) {
      this.addToAlbum = new Album();
      this.addToAlbum.name = this.query;
    }

    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushTrackToStorage(this.currentFileUpload, this.track, this.addToAlbum)
      .subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress.percentage = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.successMsg = 'Track successfully created. Please close this wizard!';
        }
      });

    this.selectedFiles = undefined;
  }


}
