import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTrackFormComponent } from './create-track-form.component';

describe('CreateTrackFormComponent', () => {
  let component: CreateTrackFormComponent;
  let fixture: ComponentFixture<CreateTrackFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTrackFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTrackFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
