import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { SearchComponent } from './search/search/search.component'

const routes: Routes = [
  { path: '', component: SearchComponent, pathMatch: 'full' },
  { path: ':ndbno', component: SearchComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
