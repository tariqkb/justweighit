import {NgModule} from '@angular/core';
import {SearchViewModel} from './search-view-model';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';
import {SearchComponent} from './search';
import {SearchInputComponent} from './components/search-input/search-input';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

const routes: Routes = [
  {path: 'search', component: SearchComponent}
];

const routing = RouterModule.forChild(routes);

@NgModule({
  declarations: [SearchComponent, SearchInputComponent],
  imports: [ReactiveFormsModule, CommonModule, SharedModule, routing],
  exports: [SharedModule],
  providers: [SearchViewModel]
})
export class SearchModule {

}
