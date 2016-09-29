import {Component, ChangeDetectionStrategy, ViewEncapsulation} from '@angular/core';
@Component({
  selector: 'app',
  template: `<router-outlet></router-outlet>`,
  styles: [require('../index.scss')],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  //nfs

  //nfe

  constructor() {

  }

}
