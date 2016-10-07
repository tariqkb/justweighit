import {Injectable} from '@angular/core';
import {Backend} from '../shared/backend';
@Injectable()
export class SearchViewModel {

  constructor(private backend: Backend) {
  }
}
