import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {routing} from './app.routing';
import {MusicHubComponent} from './music-hub/music-hub.component';
import {PersonServiceClient} from './services/person.service.client';
import {ArtistServiceClient} from './services/artist.service.client';
import {CriticServiceClient} from './services/critic.service.client';
import {FormsModule} from '@angular/forms';
import {NapsterServiceClient} from './services/napster.service.client';
import {SearchResultsComponent} from './search-results/search-results.component';
import {ResultsListComponent} from './results-list/results-list.component';
import {CriticComponent} from './critic/critic.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {DashboardComponent} from './dashboard/dashboard.component';
import {TrackComponent} from './track/track.component';
import {HttpClientModule} from '@angular/common/http';
import {CreateTrackFormComponent} from './create-track-form/create-track-form.component';
import {UploadFileService} from './upload/upload-file.service';
import {DashboardRightPaneComponent} from './dashboard-right-pane/dashboard-right-pane.component';
import {TrackServiceClient} from './services/track.service.client';
import {CreatePlaylistAlbumFormComponent} from './create-playlist-album-form/create-playlist-album-form.component';
import {FriendsComponent} from './friends/friends.component';
import {AlbumServiceClient} from './services/album.service.client';
import {PlaylistServiceClient} from './services/playlist.service.client';
import {RateFormComponent} from './rate-form/rate-form.component';
import {ModalModule} from 'angular-custom-modal';
import {ReviewFormComponent} from './review-form/review-form.component';
import {AlbumComponent} from './album/album.component';
import {ArtistComponent} from './artist/artist.component';
import {TrackToPlaylistComponent} from './track-to-playlist/track-to-playlist.component';
import {PlaylistComponent} from './playlist/playlist.component';
import {ManageComponent} from './manage/manage.component';
import {AdminServiceClient} from './services/admin.service.client';
import {ManagerComponent} from './manager/manager.component';
import {AdvertiserServiceClient} from './services/advertiser.service.client';
import {AdvertisementComponent} from './advertisement/advertisement.component';
import {CritiqueComponent} from './critique/critique.component';
import {DomainManagerComponent} from './domain-manager/domain-manager.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HomeComponent,
    MusicHubComponent,
    SearchResultsComponent,
    ResultsListComponent,
    CriticComponent,
    DashboardComponent,
    TrackComponent,
    CreateTrackFormComponent,
    DashboardRightPaneComponent,
    CreatePlaylistAlbumFormComponent,
    FriendsComponent,
    RateFormComponent,
    ReviewFormComponent,
    AlbumComponent,
    ArtistComponent,
    TrackToPlaylistComponent,
    PlaylistComponent,
    ManageComponent,
    ManagerComponent,
    AdvertisementComponent,
    CritiqueComponent,
    DomainManagerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ModalModule,
    routing,
    FontAwesomeModule,
    HttpClientModule
  ],
  providers: [DashboardRightPaneComponent, AdvertisementComponent, ArtistServiceClient, CriticServiceClient,
    PersonServiceClient, NapsterServiceClient, UploadFileService, TrackServiceClient,
    AlbumServiceClient, PlaylistServiceClient, AdminServiceClient, AdvertiserServiceClient],
  bootstrap: [AppComponent]
})
export class AppModule {
}
