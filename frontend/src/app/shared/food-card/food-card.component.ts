import { Component, Input } from '@angular/core'
import { FoodMeasurement, FoodResponse } from '../backend.types'

@Component({
  selector: 'jwi-food-card',
  templateUrl: './food-card.component.html',
  styleUrls: ['./food-card.component.scss'],
})
export class FoodCardComponent {
  @Input('food') result: FoodResponse
  @Input('loading') loading: boolean = false
  @Input('error') error: string = null

  selectedMeasurement: FoodMeasurement

  constructor() {}

  selectMeasurement(measurement: FoodMeasurement) {
    this.selectedMeasurement = measurement
  }

  ngOnChanges(changes) {
    if (changes.result) {
      if (this.result && this.result.measurements.length) {
        this.selectMeasurement(this.result.measurements[0])
      } else {
        this.selectMeasurement(null)
      }
    }
  }
}
