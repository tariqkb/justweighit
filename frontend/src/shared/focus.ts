import {Directive, Input, ElementRef, OnChanges} from '@angular/core';
import {timeout} from 'rxjs/operator/timeout';
@Directive({
  selector: '[focus]'
})
export class FocusDirective implements OnChanges {
  //nfs
	@Input('focus') when: boolean;
	@Input('if') condition: boolean = true;
	@Input('timeout') timeout: number;
	//nfe

  constructor(private el: ElementRef) {}

  ngOnChanges(changes) {
    if (changes.when) {
      if (this.when) {
        setTimeout(() => {
          if (this.condition) {
            this.el.nativeElement.focus();
          }
        }, this.timeout);
      } else {
        setTimeout(() => {

          if (this.condition) {
            this.el.nativeElement.blur();
          }
        }, this.timeout);
      }
    }
  }

}
