import {NgModule} from '@angular/core';
import {SearchViewModel} from './search-view-model';
import {SharedModule} from '../shared/shared.module';
import {RouterModule, Routes} from '@angular/router';
import {SearchComponent} from './search';

const routes: Routes = [
  {path: 'search', component: SearchComponent}
];

const routing = RouterModule.forChild(routes);

@NgModule({
  declarations: [SearchComponent],
  imports: [SharedModule, routing],
  exports: [SharedModule],
  providers: [SearchViewModel]
})
export class SearchModule {

}
