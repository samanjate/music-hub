import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardRightPaneComponent } from './dashboard-right-pane.component';

describe('DashboardRightPaneComponent', () => {
  let component: DashboardRightPaneComponent;
  let fixture: ComponentFixture<DashboardRightPaneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardRightPaneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardRightPaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
