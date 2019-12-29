import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FocusDirective } from './focus.directive'
import { FoodCardComponent } from './food-card/food-card.component'
import { ClickOutsideDirective, ClickOutsideModule } from 'ng-click-outside'
import { LoadingOverlayComponent } from './loading-overlay/loading-overlay.component'

@NgModule({
  declarations: [FocusDirective, FoodCardComponent, LoadingOverlayComponent],
  exports: [FocusDirective, FoodCardComponent, ClickOutsideDirective, LoadingOverlayComponent],
  imports: [CommonModule, ClickOutsideModule],
})
export class SharedModule {}
