import {Component, ChangeDetectionStrategy, Input} from '@angular/core';
import {FoodResponse, FoodMeasurement} from '../shared/model';
@Component({
  selector: 'food-card',
  template: require('./food-card.html'),
  styles: [require('./food-card.scss')],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FoodCardComponent {

  @Input('food') result: FoodResponse;
  @Input('loading') loading: boolean = false;
  @Input('error') error: string = null;

  selectedMeasurement: FoodMeasurement;

  constructor() {

  }

  selectMeasurement(measurement: FoodMeasurement) {
    this.selectedMeasurement = measurement;
  }

  ngOnChanges(changes) {
    if(changes.result) {
      if (this.result && this.result.measurements.length) {
        this.selectMeasurement(this.result.measurements[0]);
      } else {
        this.selectMeasurement(null);
      }
    }
  }
}
