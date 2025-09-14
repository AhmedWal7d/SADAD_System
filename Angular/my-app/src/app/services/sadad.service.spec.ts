import { TestBed } from '@angular/core/testing';

import { SadadService } from './sadad.service';

describe('SadadService', () => {
  let service: SadadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SadadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
