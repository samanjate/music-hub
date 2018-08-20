import {Privacy} from './Privacy';
import {Track} from './Track';
import {Person} from './Person';

export class Playlist {
  id: Number;
  name: String;
  trackCount: Number;
  privacy: Privacy;
  description: String;
  tracks: Track [];
  createdBy: Person;
  imageURL: String;
}
