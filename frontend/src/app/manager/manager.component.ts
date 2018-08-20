import {Component, Input, OnInit} from '@angular/core';
import {CriticServiceClient} from '../services/critic.service.client';
import {ArtistServiceClient} from '../services/artist.service.client';
import {AdvertiserServiceClient} from '../services/advertiser.service.client';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  @Input() entity: string;
  resultSet: any;
  edit: boolean;
  newResult: any = {};

  constructor(private artistService: ArtistServiceClient,
              private criticService: CriticServiceClient,
              private advertiserService: AdvertiserServiceClient) { }

  ngOnInit() {
    if (this.entity === 'critic') {
      this.loadCritics();
    } else if (this.entity === 'artist') {
      this.loadArtists();
    } else if (this.entity === 'advertiser') {
      this.loadAdvertisers();
    }
  }

  loadCritics() {
    this.criticService.findAllCritics()
      .subscribe(response => {
          this.resultSet = response;
        }
      , () => alert('Couldn\'t load critics!'));
  }

  loadArtists() {
    this.artistService.findAllArtists()
      .subscribe(response => {
          this.resultSet = response;
        }
        , () => alert('Couldn\'t load artists!'));
  }

  loadAdvertisers() {
    this.advertiserService.findAllAdvertisers()
      .subscribe(response => {
          this.resultSet = response;
        }
        , () => alert('Couldn\'t load advertisers!'));
  }

  update(result) {
    if (this.entity === 'critic') {
      this.criticService.updateCritic(result)
        .subscribe(() => {
          this.edit = false;
        }, () =>  {
          alert('Couldn\'t edit critic!');
          this.edit = false;
        });
    } else if (this.entity === 'artist') {
      this.artistService.updateArtist(result)
        .subscribe(() => {
          this.edit = false;
        }, () =>  {
          alert('Couldn\'t edit artist!');
          this.edit = false;
        });
    } else if (this.entity === 'advertiser') {
      this.advertiserService.updateAdvertiser(result)
        .subscribe(() => {
          this.edit = false;
        }, () =>  {
          alert('Couldn\'t edit advertiser!');
          this.edit = false;
        });
    }
  }

  add() {
    if (this.entity === 'critic') {
      this.criticService.createCritic(this.newResult)
        .subscribe(response => {
          this.resultSet.push(response);
        }, () =>  {
          alert('Couldn\'t create critic!');
        });
    } else if (this.entity === 'artist') {
      this.artistService.createArtist(this.newResult)
        .subscribe(response => {
          this.resultSet.push(response);
        }, () =>  {
          alert('Couldn\'t create artist!');
        });
    } else if (this.entity === 'advertiser') {
      this.advertiserService.createAdvertiser(this.newResult)
        .subscribe(response => {
          this.resultSet.push(response);
        }, () =>  {
          alert('Couldn\'t create advertiser!');
        });
    }
  }

  delete(id) {
    if (this.entity === 'critic') {
      this.criticService.deleteCritic(id)
        .subscribe(response => {
          if (response === 'OK') {
            let index = 0;
            let deleteIndex = -1;
            this.resultSet.forEach(function (entry) {
              if (entry.id === id) {
                deleteIndex = index;
              }
              index++;
            });
            if (deleteIndex !== -1) {
              this.resultSet.splice(deleteIndex);
            }
          }
        }, () =>  {
          alert('Couldn\'t delete critic!');
        });
    } else if (this.entity === 'artist') {
      this.artistService.deleteArtist(id)
        .subscribe(response => {
          if (response === 'OK') {
            let index = 0;
            let deleteIndex = -1;
            this.resultSet.forEach(function (entry) {
              if (entry.id === id) {
                deleteIndex = index;
              }
              index++;
            });
            if (deleteIndex !== -1) {
              this.resultSet.splice(deleteIndex);
            }
          }
        }, () =>  {
          alert('Couldn\'t delete artist!');
        });
    } else if (this.entity === 'advertiser') {
      this.advertiserService.deleteAdvertiser(id)
        .subscribe(response => {
          if (response === 'OK') {
            let index = 0;
            let deleteIndex = -1;
            this.resultSet.forEach(function (entry) {
              if (entry.id === id) {
                deleteIndex = index;
              }
              index++;
            });
            if (deleteIndex !== -1) {
              this.resultSet.splice(deleteIndex);
            }
          }
        }, () =>  {
          alert('Couldn\'t delete advertiser!');
        });
    }
  }
}
