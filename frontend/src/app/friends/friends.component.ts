import {ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Person} from '../models/Person';
import {PersonServiceClient} from '../services/person.service.client';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit, OnChanges {
  @Input()
  resultList;
  @Input()
  domainObject;

  person: Person;

  constructor(private personService: PersonServiceClient) {
  }

  ngOnInit() {
    this.refreshPage();
  }

  refreshPage() {
    this.personService.checkSession().subscribe(
      (person: Person) => {
        this.person = person;
        if (this.resultList) {
          this.resultList = this.resultList.map(personByUsername => {
            if (personByUsername.followers) {
              personByUsername['follow'] = personByUsername.followers.map(eachFollowers => {
                if (eachFollowers.id === this.person.id) {
                  return true;
                } else {
                  return false;
                }
              });
            }
            return personByUsername;

          });

          this.resultList.map(p => {
            if (p.follow && p.follow.includes(true)) {
              p.follow = true;
            } else {
              p.follow = false;
            }
          });
        }
      });
  }

  ngOnChanges(changes: SimpleChanges) {
    this.refreshPage();
  }

  followPerson(followButton, anotherPerson) {
    this.personService.followPerson(this.person.id, anotherPerson)
      .subscribe((response) => {
        followButton.style.backgroundColor = 'green';
        followButton.innerHTML = 'unfollow';
        followButton.addEventListener('click', (e: Event) => this.unfollowPerson(followButton, anotherPerson));
      });
  }

  unfollowPerson(unfollowButton, anotherPerson) {
    this.personService.unfollowPerson(this.person.id, anotherPerson)
      .subscribe((response) => {
        unfollowButton.style.backgroundColor = 'blue';
        unfollowButton.innerHTML = 'follow';
        unfollowButton.addEventListener('click', (e: Event) => this.followPerson(unfollowButton, anotherPerson));

      });
  }
}
