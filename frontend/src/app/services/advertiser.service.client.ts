import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AdvertiserServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  ADVERTISER_URL: string = this.DOMAIN_URL + '/api/advertiser';

  constructor(private http: HttpClient) {
  }

  findAllAdvertisers() {
    return this.http.get(this.ADVERTISER_URL);
  }

  deleteAdvertiser(id) {
    return this.http.delete(this.ADVERTISER_URL + '/' + id);
  }

  updateAdvertiser(advertiser) {
    console.log(this.ADVERTISER_URL + '/' + advertiser.id);
    return this.http.put(this.ADVERTISER_URL + '/' + advertiser.id, advertiser);
  }

  createAdvertiser(advertiser) {
    return this.http.post(this.ADVERTISER_URL, advertiser);
  }

  createAdvertisementsCreatedInNapsterArtistPage(artistId, advertiser, ad) {
    return this.http.post(this.ADVERTISER_URL + '/create/advertisement/napster?artist=' + artistId + '&advertiser=' + advertiser.id, ad);
  }

  createAdvertisementsCreatedInArtistPage(artistId, advertiser, ad) {
    return this.http.post(this.ADVERTISER_URL + '/create/advertisement?artist=' + artistId + '&advertiser=' + advertiser.id, ad);
  }

  findAdvertisementsByNapsterArtist(napsterArtistId, advertiserId) {
    return this.http.get(this.DOMAIN_URL + '/api/advertisement/' + napsterArtistId + '/in/napster/artist/' + advertiserId);
  }

  findAdvertisementsByAdvertiser(advertiserId) {
    return this.http.get(this.DOMAIN_URL + '/api/advertisement/advertiser/' + advertiserId);
  }
}
