import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Output,
  ViewChild,
} from '@angular/core'
import { FoodSearchResponse } from '../../shared/backend.types'
import { Title } from '@angular/platform-browser'
import { fromEvent, merge, Observable, of, Subscription } from 'rxjs'
import { Backend } from '../../shared/api/api'
import { FormControl } from '@angular/forms'
import {
  catchError,
  debounceTime,
  filter,
  map,
  share,
  switchMap,
  withLatestFrom,
} from 'rxjs/operators'

@Component({
  selector: 'jwi-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss'],
})
export class SearchInputComponent {
  @ViewChild('searchInput', { static: true }) searchInput: ElementRef
  @Output() select = new EventEmitter<FoodSearchResponse>()

  results: Observable<FoodSearchResponse[]>
  loading: Observable<boolean>

  inputActive: boolean = false
  searchControl: FormControl
  activeValue: FoodSearchResponse
  subs: Subscription[] = []

  constructor(
    private backend: Backend,
    private changeRef: ChangeDetectorRef,
    private title: Title
  ) {
    this.searchControl = new FormControl()

    let searchInputChange = this.searchControl.valueChanges.pipe(share())

    this.subs.push(
      searchInputChange.subscribe(text => {
        this.inputActive = !!text
      })
    )

    this.results = searchInputChange.pipe(
      debounceTime(500),
      filter(text => text),
      switchMap(text => this.backend.search(text)),
      map(r => r.foods),
      catchError(err => of([])),
      share()
    )

    this.loading = merge(
      searchInputChange.pipe(map(_ => true)),
      this.results.pipe(map(_ => false))
    ).pipe(share())
  }

  selectResult(val: FoodSearchResponse) {
    this.select.emit(val)
    this.title.setTitle(this.searchControl.value)
    this.clear()
  }

  clear() {
    this.activeValue = null
    this.inputActive = false
    this.searchControl.setValue('')
  }

  onKeydown(event: KeyboardEvent) {
    if (event.keyCode === 9) {
      this.clear()
    }
  }

  onFocus() {
    if (this.searchControl.value) {
      this.inputActive = true
    }
  }

  ngAfterViewInit() {
    this.configKeyboardNav()
  }

  configKeyboardNav() {
    this.subs.push(
      fromEvent(this.searchInput.nativeElement, 'keydown')
        .pipe(withLatestFrom(this.results))
        .subscribe(res => {
          let event = <KeyboardEvent>res[0]
          let results = res[1]

          let key = event.keyCode

          this.inputActive = true

          if (key !== 40 && key !== 38 && key !== 13) {
            return
          }

          // if (!this.activeValue) {
          //   this.activeValue = filteredMembers[0];
          // }

          if (key === 40) {
            if (
              !this.activeValue ||
              this.activeValue === results[results.length - 1]
            ) {
              this.activeValue = results[0]
            } else {
              this.activeValue = results[results.indexOf(this.activeValue) + 1]
            }
          } else if (key == 38) {
            if (!this.activeValue || this.activeValue === results[0]) {
              this.activeValue = results[results.length - 1]
            } else {
              this.activeValue = results[results.indexOf(this.activeValue) - 1]
            }
          } else if (key == 13 && this.activeValue) {
            this.selectResult(this.activeValue)
          }

          this.changeRef.markForCheck()
        })
    )
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }
}
