import {NgModule} from '@angular/core/src/metadata/ng_module';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpModule} from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app';
import {Backend} from '../shared/backend';
import {SharedModule} from '../shared/shared.module';
import {SearchModule} from '../search/search.module';
import {Routes, RouterModule} from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: '/search', pathMatch: 'full'}
];

const routing = RouterModule.forRoot(routes, {useHash: true});

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, HttpModule, CommonModule, FormsModule, ReactiveFormsModule, routing, SharedModule, SearchModule],
  providers: [Backend
    // {provide: EVENT_MANAGER_PLUGINS, useClass: DOMOutsideEventPlugin, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
