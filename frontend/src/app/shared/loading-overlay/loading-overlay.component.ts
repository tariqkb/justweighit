import { Component, ElementRef, HostBinding, Input, OnInit } from '@angular/core'

@Component({
  selector: 'jwi-loading-overlay',
  templateUrl: './loading-overlay.component.html',
  styleUrls: ['./loading-overlay.component.scss'],
})
export class LoadingOverlayComponent {
  @Input() active: boolean
  @Input() error: string
  @Input() color: string = '#000000'
  @Input() size: number = 100

  @HostBinding('class.visible') visible: boolean
  @HostBinding('class.hidden') hidden: boolean

  constructor(private element: ElementRef) {}

  ngOnChanges() {
    this.visible = !!this.active
    this.hidden = !this.active
  }
}
