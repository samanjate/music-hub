import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackToPlaylistComponent } from './track-to-playlist.component';

describe('TrackToPlaylistComponent', () => {
  let component: TrackToPlaylistComponent;
  let fixture: ComponentFixture<TrackToPlaylistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackToPlaylistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackToPlaylistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
