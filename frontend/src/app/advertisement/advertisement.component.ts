import {Component, Input, OnInit} from '@angular/core';
import {AdvertiserServiceClient} from '../services/advertiser.service.client';

@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {

  @Input() artistId;
  @Input() advertiserId;
  @Input() isNapst;

  adsPostedUnderArtist;

  constructor(private advertiserService: AdvertiserServiceClient) {
  }

  ngOnInit() {
    if (this.isNapst) {
      this.advertiserService.findAdvertisementsByNapsterArtist(this.artistId, this.advertiserId)
        .subscribe(response => {
            this.adsPostedUnderArtist = response;
          }
        );
    }
  }
}
