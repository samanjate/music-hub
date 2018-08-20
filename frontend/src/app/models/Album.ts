import {Track} from './Track';
import {Artist} from './Artist';

export class Album {
  id: Number;
  napsterId: Number;
  name: string;
  imageURL: string;
  artistName: string;
  tracks: Track[];
  artist: Artist;
}
