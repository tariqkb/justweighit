import {
  Component, ChangeDetectionStrategy, ElementRef, ChangeDetectorRef, AfterViewInit,
  EventEmitter, Output
} from '@angular/core';
import {SearchViewModel} from '../../search-view-model';
import {Backend} from '../../../shared/backend';
import {FoodSearchResponse, SearchResponse} from '../../../shared/model';
import {Observable, Subscription} from 'rxjs';
import {FormControl} from '@angular/forms';
import {ViewChild} from '@angular/core/src/metadata/di';
import {RequestMethod} from '@angular/http';
import {Title} from '@angular/platform-browser';
@Component({
  selector: 'search-input',
  template: require('./search-input.html'),
  styles: [require('./search-input.scss')],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchInputComponent implements AfterViewInit {

  @ViewChild('searchInput') searchInput: ElementRef;
  @Output() select = new EventEmitter<FoodSearchResponse>();

  results: Observable<FoodSearchResponse[]>;
  loading: Observable<boolean>;

  inputActive: boolean = false;
  searchControl: FormControl;
  activeValue: FoodSearchResponse;
  subs: Subscription[] = [];

  constructor(private backend: Backend, private svm: SearchViewModel, private changeRef: ChangeDetectorRef, private title: Title) {
    this.searchControl = new FormControl();

    let searchInputChange = this.searchControl.valueChanges.share();

    this.subs.push(searchInputChange.subscribe(text => {
      this.inputActive = !!text;
    }));

    this.results = searchInputChange.debounceTime(500).filter(text => text).switchMap(text => {
      return this.backend.request<SearchResponse>(RequestMethod.Post, 'search', {text}).map(r => r.foods)
        .catch(err => {
          return Observable.of([]);
        });
    }).share();

    this.loading = searchInputChange.map(_ => true).merge(this.results.map(_ => false)).share();
  }

  selectResult(val: FoodSearchResponse) {
    this.select.emit(val);
    this.title.setTitle(this.searchControl.value);
    this.clear();
  }

  clear() {
    this.activeValue = null;
    this.inputActive = false;
    this.searchControl.setValue('');
  }

  onKeydown(event: KeyboardEvent) {
    if (event.keyCode === 9) {
      this.clear();
    }
  }

  onFocus() {
    if (this.searchControl.value) {
      this.inputActive = true;
    }
  }

  ngAfterViewInit() {
    this.configKeyboardNav();
  }

  configKeyboardNav() {
    this.subs.push(Observable.fromEvent(this.searchInput.nativeElement, 'keydown').withLatestFrom(this.results).subscribe(res => {
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
    }));
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
