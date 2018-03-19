import {Component, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {FoodSearchResponse, FoodResponse} from '../shared/model';
import {Backend} from '../shared/backend';
import {RequestMethod} from '@angular/http';
import {Router, ActivatedRoute} from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {Title} from '@angular/platform-browser';
@Component({
  selector: 'search',
  template: require('./search.html'),
  styles: [require('./search.scss')],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent {

  result: FoodResponse;
  loading: boolean = false;
  error: string;

  subs: Subscription[] = [];

  constructor(private backend: Backend, private router: Router, private route: ActivatedRoute, private changeRef: ChangeDetectorRef,
              private title: Title) {
    let params = this.route.params;

    this.subs.push(params.subscribe((p: {ndbno: string, amount: string, unit: string}) => {
      this.result = null;
      this.error = null;
      if (p.ndbno) {
        this.loading = true;
      }

      this.changeRef.markForCheck();
    }));

    this.subs.push(params.switchMap(params => {
      const ndbno = params['ndbno'];
      const amount = this.route.snapshot.queryParams['amount'];
      const unit = this.route.snapshot.queryParams['unit'];

      if (!ndbno) {
        return Observable.of(null);
      }

      let queryParams: any = {};
      if(amount) {
        queryParams.amount = amount;
      }
      if(unit) {
        queryParams.unit = unit;
      }

      return this.backend.request<FoodResponse>(RequestMethod.Get, `food/${ndbno}`, {}, queryParams);
    }).delay(2000)
      .catch(() => {
        this.error = "Oops, something went wrong.";
        return Observable.never();
      })
      .subscribe(foodResponse => {
        this.result = foodResponse;
        this.loading = false;

        this.changeRef.markForCheck();
      }));
  }

  select(food: FoodSearchResponse) {
    if (food.ndbno) {
      this.loading = true;

      let queryParams: any = {};
      if (food.amount) {
        queryParams.amount = food.amount;
      }
      if (food.unit) {
        queryParams.unit = food.unit;
      }
      this.router.navigate([food.ndbno], {queryParams: queryParams});
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe());
  }

}
