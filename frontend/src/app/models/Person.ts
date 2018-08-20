import {Album} from './Album';

export class Person {
  id: Number;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  selfBio: string;
  dType: string;
  userType: string;
  followers: Person[];
  following: Person[];
  albums: Album[];
}
