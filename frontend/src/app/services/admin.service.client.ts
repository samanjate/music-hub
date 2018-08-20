import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class AdminServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  ADMIN_URL = this.DOMAIN_URL + '/api/admin/';

  constructor(private http: HttpClient) {
  }

  updateAdmin(admin) {
    return this.http.put(this.ADMIN_URL + '/' + admin.id, admin);
  }

}
