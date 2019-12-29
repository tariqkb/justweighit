import { ChangeDetectorRef, Component } from '@angular/core'
import { Backend } from '../../shared/api/api'
import { ActivatedRoute, Params, Router } from '@angular/router'
import { Title } from '@angular/platform-browser'
import { FoodResponse, FoodSearchResponse } from '../../shared/backend.types'
import { combineLatest, Observable, of, Subject } from 'rxjs'
import { delay, flatMap, switchMap, takeUntil, tap } from 'rxjs/operators'

@Component({
  selector: 'jwi-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent {
  result: Observable<FoodResponse>
  loading: boolean = false
  error: string

  _unsubscribe = new Subject()

  constructor(
    private backend: Backend,
    private router: Router,
    private route: ActivatedRoute,
    private title: Title
  ) {
    // this.backend.getFood('11413', '0.5', 'cup').subscribe(console.log)
    this.result = combineLatest(this.route.params, this.route.queryParams).pipe(
      takeUntil(this._unsubscribe),
      tap(([params, _]) => this.resetSearch(params)),
      switchMap(([params, queryParams]) => {
        console.log(params)
        if (!params.ndbno) {
          return of(null)
        }
        return this.backend.getFood(
          params.ndbno,
          queryParams.amount,
          queryParams.unit
        )
      }),
      delay(2000),
      tap(() => (this.loading = false))
    )
  }

  select(food: FoodSearchResponse) {
    console.log(food)
    if (food.ndbno) {
      this.loading = true

      let queryParams: any = {}
      if (food.amount) {
        queryParams.amount = food.amount
      }
      if (food.unit) {
        queryParams.unit = food.unit
      }
      return this.router.navigate([food.ndbno], { queryParams: queryParams })
    }
  }

  resetSearch(params: Params) {
    this.error = null
    if (params.ndbno) {
      this.loading = true
    }
  }

  ngOnDestroy() {
    this._unsubscribe.next()
    this._unsubscribe.complete()
  }
}
