import {Component, ElementRef, Input, ChangeDetectionStrategy, HostBinding} from '@angular/core';
@Component({
  selector: 'loading-overlay',
  styles: [require('./loading-overlay.scss')],
  template: require('./loading-overlay.html'),
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadingOverlayComponent {

  //nfs
	@Input() active: boolean;
	@Input() error: string;
	@Input() color: string = "#000000";
	@Input() size: number = 100;

  @HostBinding('class.visible') visible: boolean;
  @HostBinding('class.hidden') hidden: boolean;
	//nfe

  constructor(private element: ElementRef) {
  }

  ngOnChanges() {
    this.visible = !!this.active;
    this.hidden = !this.active;
  }

}
