import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SadadReleaseComponent } from './sadad-release.component';

describe('SadadReleaseComponent', () => {
  let component: SadadReleaseComponent;
  let fixture: ComponentFixture<SadadReleaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SadadReleaseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SadadReleaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
