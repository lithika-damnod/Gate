import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  standalone: false
})
export class EventCardComponent {
  @Input() src!: string;
  @Input() href!: string;
  @Input() name!: string;
  @Input() venue!: string;
  @Input() time!: string;
}
