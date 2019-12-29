import { BrowserModule } from '@angular/platform-browser'
import { NgModule } from '@angular/core'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { SearchModule } from './search/search.module'
import { Backend } from './shared/api/api'
import { HttpClientModule } from '@angular/common/http'
import { CommonModule } from '@angular/common'

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, CommonModule, AppRoutingModule, SearchModule, HttpClientModule],
  providers: [Backend],
  bootstrap: [AppComponent],
})
export class AppModule {}
