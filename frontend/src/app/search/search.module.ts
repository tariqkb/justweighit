import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { SearchInputComponent } from './search-input/search-input.component'
import { SearchComponent } from './search/search.component'
import { SharedModule } from '../shared/shared.module'
import { RouterModule, Routes } from '@angular/router'
import { ReactiveFormsModule } from '@angular/forms'

const routes: Routes = [{ path: 'search', component: SearchComponent }]

const routing = RouterModule.forChild(routes)

@NgModule({
  declarations: [SearchInputComponent, SearchComponent],
  imports: [CommonModule, routing, SharedModule, ReactiveFormsModule],
})
export class SearchModule {}
