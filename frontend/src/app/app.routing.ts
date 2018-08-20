import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SearchResultsComponent} from './search-results/search-results.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {TrackComponent} from './track/track.component';
import {AlbumComponent} from './album/album.component';
import {ArtistComponent} from './artist/artist.component';
import {CreateTrackFormComponent} from './create-track-form/create-track-form.component';
import {PlaylistComponent} from './playlist/playlist.component';
import {ManageComponent} from './manage/manage.component';

const appRoutes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'manage', component: ManageComponent},
  {path: 'track/:id', component: TrackComponent},
  {path: 'album/:id', component: AlbumComponent},
  {path: 'artist/:id', component: ArtistComponent},
  {path: 'playlist/:id', component: PlaylistComponent},
  {path: 'search/:query', component: SearchResultsComponent},
  {path: 'create/track', component: CreateTrackFormComponent},
  {path: '**', component: AppComponent}
];
export const routing = RouterModule.forRoot(appRoutes);
