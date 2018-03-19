import {NgModule} from '@angular/core/src/metadata/ng_module';
import {LoadingOverlayComponent} from './loading-overlay/loading-overlay';
import {FoodCardComponent} from '../food-card/food-card';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';
import {FocusDirective} from './focus';
@NgModule({
  declarations: [LoadingOverlayComponent, FocusDirective, FoodCardComponent],
  exports: [LoadingOverlayComponent, FocusDirective, FoodCardComponent],
  imports: [BrowserModule, HttpModule, CommonModule, FormsModule, ReactiveFormsModule]
})
export class SharedModule {

}
