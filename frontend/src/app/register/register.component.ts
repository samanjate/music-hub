import {Component, OnInit} from '@angular/core';
import {Person} from '../models/Person';
import {ArtistServiceClient} from '../services/artist.service.client';
import {CriticServiceClient} from '../services/critic.service.client';
import {Router} from '@angular/router';
import {AdvertiserServiceClient} from '../services/advertiser.service.client';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css', '../../assets/css/style.css']
})
export class RegisterComponent implements OnInit {

  person: Person;
  ng = this;

  constructor(private router: Router,
              private artistService: ArtistServiceClient,
              private criticService: CriticServiceClient,
              private advertiserService: AdvertiserServiceClient) {
  }

  ngOnInit() {
    this.person = new Person();
    this.person.username = '';
    this.person.password = '';
    this.person.confirmPassword = '';
    this.person.userType = '';
  }

  register(p) {
    if (p.userType === 'Artist') {
      this.artistService.createArtist(p)
        .subscribe(createdPerson => this.router.navigate(['dashboard']));
    } else if (p.userType === 'Critic') {
      this.criticService.createCritic(p)
        .subscribe(createdPerson => this.router.navigate(['dashboard']));
    } else if (p.userType === 'Advertiser') {
      this.advertiserService.createAdvertiser(p)
        .subscribe(createdPerson => this.router.navigate(['dashboard']));
    }
  }
}
