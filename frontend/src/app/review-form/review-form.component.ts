import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CriticServiceClient} from '../services/critic.service.client';
import {Album} from '../models/Album';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {

  @Input() album: Album;
  title: string;
  text: string;
  reviewId: number;
  reviewed: any;
  submitted: any;
  deleted: any;

  constructor(private route: ActivatedRoute,
              private criticService: CriticServiceClient) {
    this.title = '';
    this.text = '';
  }

  ngOnInit() {
    this.reviewStatus();
  }

  reviewStatus() {
    this.criticService.reviewStatus(Number(this.album.id.toString().split('.').pop()))
      .subscribe(response => {
        if (response) {
          this.title = response['title'];
          this.text = response['text'];
          this.reviewId = response['id'];
          this.reviewed = true;
        } else {
          this.reviewed = false;
        }
      },
        error => alert('Couldn\'t get review status'));
  }

  reviewAlbum() {
    this.album.id = Number(this.album.id.toString().split('.').pop());
    this.album['reviews'] = [{title: this.title, text: this.text}];
    this.criticService.reviewAlbum(this.album)
      .subscribe(response => {
        if (response === 'OK') {
          this.submitted = true;
        }
        },
          error => alert('Couldn\'t review this album!'));
  }

  updateReviewAlbum() {
    this.album.id = Number(this.album.id.toString().split('.').pop());
    this.album['reviews'] = [{title: this.title, text: this.text}];
    this.criticService.updateReviewAlbum(this.album)
      .subscribe(response => {
          if (response === 'OK') {
            this.submitted = true;
          }
        },
        error => alert('Couldn\'t update the review for this album!'));
  }

  deleteReviewAlbum() {
    this.criticService.deleteReviewAlbum(this.reviewId)
      .subscribe(response => {
          console.log(response);
          if (response === 'OK') {
            this.deleted = true;
          }
        },
        error => alert('Couldn\'t delete the review for this album!'));
  }

}
