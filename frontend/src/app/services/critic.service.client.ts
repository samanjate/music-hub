import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class CriticServiceClient {

  DOMAIN_URL = 'https://music-hub-app-springboot.herokuapp.com';
  CRITIC_URL = this.DOMAIN_URL + '/api/critic';
  LIKE_URL = this.DOMAIN_URL + '/api/critic/like';
  UNLIKE_URL = this.DOMAIN_URL + '/api/critic/unlike';
  RATE_URL = this.DOMAIN_URL + '/api/critic/rate';
  REVIEW_URL = this.DOMAIN_URL + '/api/critic/review';

  constructor(private http: HttpClient) {
  }

  findAllCritics() {
   return this.http.get(this.CRITIC_URL);
  }

  createCritic(critic) {
    return this.http.post(this.CRITIC_URL, critic);
  }

  updateCritic(critic) {
    return this.http.put(this.CRITIC_URL + '/' + critic.id, critic);
  }

  deleteCritic(id) {
    return this.http.delete(this.CRITIC_URL + '/' + id);
  }

  likeStatus(tid) {
    return this.http.get(this.LIKE_URL + '/' + tid);
  }

  likeTrack(tid, track) {
    return this.http.post(this.LIKE_URL + '/' + tid, track);
  }

  unlikeTrack(tid) {
    return this.http.delete(this.UNLIKE_URL + '/' + tid);
  }

  rateTrack(rating, track) {
    return this.http.post(this.RATE_URL + '/' + rating, track);
  }

  updateRateTrack(rating, track) {
    return this.http.put(this.RATE_URL + '/' + rating, track);
  }

  rateStatus(tid) {
    return this.http.get(this.RATE_URL + '/' + tid);
  }

  reviewAlbum(album) {
    return this.http.post(this.REVIEW_URL, album);
  }

  reviewStatus(aid) {
    return this.http.get(this.REVIEW_URL + '/' + aid);
  }

  updateReviewAlbum(album) {
    return this.http.put(this.REVIEW_URL, album);
  }

  deleteReviewAlbum(rid) {
    return this.http.delete(this.REVIEW_URL + '/' + rid);
  }

  getLikes(id) {
    return this.http.get(this.LIKE_URL + '?critic=' + id);
  }

  getRatings(id) {
    return this.http.get(this.RATE_URL + '?cid=' + id);
  }

  getReviews(id) {
    return this.http.get(this.REVIEW_URL + '?cid=' + id);
  }
}
