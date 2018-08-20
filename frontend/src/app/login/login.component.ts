import {Component, OnInit} from '@angular/core';
import {PersonServiceClient} from '.././services/person.service.client';
import {Person} from '.././models/Person';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../assets/css/style.css']
})
export class LoginComponent implements OnInit {

  person: Person;

  constructor(private router: Router,
              private personService: PersonServiceClient) {
  }

  ngOnInit() {
    this.person = new Person();
  }

  login(p) {
    this.personService.login(p.username, p.password)
      .subscribe((message: String) => {
          if (message === 'OK') {
            this.router.navigate(['dashboard']);
          } else {
            alert('Invalid credentials');
          }
        },
        error => alert('Invalid credentials!'));
  }
}
