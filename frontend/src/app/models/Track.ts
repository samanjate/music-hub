import {Artist} from './Artist';

export class Track {
  id: Number;
  napsterId: Number;
  name: String;
  playbackSeconds: Number;
  previewURL: String;
  artistName: String;
  artist: Artist;
}
