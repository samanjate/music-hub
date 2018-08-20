import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePlaylistAlbumFormComponent } from './create-playlist-album-form.component';

describe('CreatePlaylistAlbumFormComponent', () => {
  let component: CreatePlaylistAlbumFormComponent;
  let fixture: ComponentFixture<CreatePlaylistAlbumFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatePlaylistAlbumFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePlaylistAlbumFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
