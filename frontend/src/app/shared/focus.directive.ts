import { Directive, Input, ElementRef, OnChanges } from '@angular/core'
@Directive({
  selector: '[focus]',
})
export class FocusDirective implements OnChanges {
  @Input('focus') when: boolean
  @Input('if') condition: boolean = true
  @Input('timeout') timeout: number

  constructor(private el: ElementRef) {}

  ngOnChanges(changes) {
    if (changes.when) {
      if (this.when) {
        setTimeout(() => {
          if (this.condition) {
            this.el.nativeElement.focus()
          }
        }, this.timeout)
      } else {
        setTimeout(() => {
          if (this.condition) {
            this.el.nativeElement.blur()
          }
        }, this.timeout)
      }
    }
  }
}
