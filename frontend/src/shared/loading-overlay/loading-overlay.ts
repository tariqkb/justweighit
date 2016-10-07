import {Component, ElementRef, Input, ChangeDetectionStrategy, HostBinding} from '@angular/core';
@Component({
  selector: 'loading-overlay',
  styles: [require('./loading-overlay.scss')],
  template: `<div><i class="fa fa-circle-o-notch fa-spin-4x"></i></div>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadingOverlayComponent {

  //nfs
	@Input() active: boolean;
  
  @HostBinding('class.visible') visible: boolean;
  @HostBinding('class.hidden') hidden: boolean;
	//nfe

  constructor(private element: ElementRef) {
  }

  ngOnChanges() {
    this.visible = this.active;
    this.hidden = !this.active;
  }

}
