import {Component, OnInit} from '@angular/core';
import {AppComponent} from '../app.component';
import {Person} from '../models/Person';
import {PersonServiceClient} from '../services/person.service.client';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css', '../../assets/css/style.css']
})
export class DashboardComponent implements OnInit {

  person: Person;

  constructor(private appComp: AppComponent, private personService: PersonServiceClient) {
    appComp.ngOnInit();
  }

  ngOnInit() {
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
        this.personService.findUserFollowing(this.person.id).subscribe((response: Person[]) => {
          this.person.following = response;
        });
      });
  }

}
