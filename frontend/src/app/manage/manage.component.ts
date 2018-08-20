import { Component, OnInit } from '@angular/core';
import {PersonServiceClient} from '../services/person.service.client';
import {Person} from '../models/Person';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css', '../../assets/css/style.css']
})
export class ManageComponent implements OnInit {

  person: Person;

  constructor(private personService: PersonServiceClient) {
    this.personService.checkSession().subscribe(
      (person: Person) => this.person = person, () => alert('could not load user')
    );
  }

  ngOnInit() {
  }

  manage(domain) {
    console.log(domain);
  }

}
