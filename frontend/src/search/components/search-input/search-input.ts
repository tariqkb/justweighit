import {Component, ChangeDetectionStrategy, ElementRef, ChangeDetectorRef, AfterViewInit} from '@angular/core';
import {SearchViewModel} from '../../search-view-model';
import {Backend} from '../../../shared/backend';
import {FoodSearchResponse, SearchResponse} from '../../../shared/model';
import {Observable} from 'rxjs';
import {FormControl} from '@angular/forms';
import {ViewChild} from '@angular/core/src/metadata/di';
import {RequestMethod} from '@angular/http';
@Component({
  selector: 'search-input',
  template: require('./search-input.html'),
  styles: [require('./search-input.scss')],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchInputComponent implements AfterViewInit {

  //nfs
  @ViewChild('searchInput') searchInput: ElementRef;
  //nfe

  results: Observable<FoodSearchResponse[]>;
  loading: Observable<boolean>;

  inputActive: boolean = false;
  searchControl: FormControl;
  activeValue: FoodSearchResponse;

  constructor(private backend: Backend, private svm: SearchViewModel, private changeRef: ChangeDetectorRef) {
    this.searchControl = new FormControl();

    let searchInputChange = this.searchControl.valueChanges.share();

    this.results = searchInputChange.debounceTime(200).switchMap(text => {
      return this.backend.request<SearchResponse>(RequestMethod.Post, 'search', {text}).map(r => r.foods)
        .catch(err => {
          return Observable.of([]);
        });
    }).cache();

    this.loading = searchInputChange.map(_ => true).merge(this.results.map(_ => false)).cache();

    setTimeout(() => this.searchControl.setValue(''));
  }

  selectResult(val: FoodSearchResponse) {

  }

  clear() {
    this.inputActive = false;
    this.searchControl.setValue('');
  }

  onKeydown(event: KeyboardEvent) {
    if (event.keyCode === 9) {
      this.clear();
    }
  }

  onFocus() {this.inputActive = true;}

  ngAfterViewInit() {
    this.configKeyboardNav();
  }

  configKeyboardNav() {
    Observable.fromEvent(this.searchInput.nativeElement, 'keydown').withLatestFrom(this.results).subscribe(res => {
      let event = <KeyboardEvent> res[0];
      let results = res[1];

      let key = event.keyCode;

      this.inputActive = true;

      if (key !== 40 && key !== 38 && key !== 13) { return; }

      // if (!this.activeValue) {
      //   this.activeValue = filteredMembers[0];
      // }

      if (key === 40) {
        if (!this.activeValue || this.activeValue === results[results.length - 1]) {
          this.activeValue = results[0];
        } else {
          this.activeValue = results[results.indexOf(this.activeValue) + 1];
        }
      } else if (key == 38) {
        if (!this.activeValue || this.activeValue === results[0]) {
          this.activeValue = results[results.length - 1];
        } else {
          this.activeValue = results[results.indexOf(this.activeValue) - 1];
        }
      } else if (key == 13 && this.activeValue) {
        this.selectResult(this.activeValue);
      }

      this.changeRef.markForCheck();
    });
  }

}
