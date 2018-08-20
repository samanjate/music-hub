import { Component, OnInit, Input } from '@angular/core';
import {CriticServiceClient} from '../services/critic.service.client';
import {ActivatedRoute} from '@angular/router';
import {Track} from '../models/Track';

@Component({
  selector: 'app-rate-form',
  templateUrl: './rate-form.component.html',
  styleUrls: ['./rate-form.component.css']
})
export class RateFormComponent implements OnInit {

  rating: any;
  prevRating: any;
  rated: any;
  @Input() track: Track;

  constructor(private route: ActivatedRoute,
              private criticService: CriticServiceClient) {
    this.rating = 0;
  }

  ngOnInit() {
    this.rated = false;
    if (this.track) {
      this.rateStatus(this.track.id);
    }
  }

  rateTrack() {
    this.track.id = Number(this.track.id.toString().split('.').pop());
    this.criticService.rateTrack(this.rating, this.track)
      .subscribe(response => {
        this.rated = true;
      },
        error => alert('Couldn\'t rate this track'));
  }

  updateRateTrack() {
    this.track.id = Number(this.track.id.toString().split('.').pop());
    this.criticService.updateRateTrack(this.rating, this.track)
      .subscribe(response => {
          this.rated = true;
        },
        error => alert('Couldn\'t re-rate this track'));
  }

  rateStatus(tid) {
    this.criticService.rateStatus(Number(tid.toString().split('.').pop()))
      .subscribe(response => {
        if (response) {
          this.rating = response['points'];
          this.prevRating = response['points'];
        }
      }, error => alert('No rate status.'));
  }

}
