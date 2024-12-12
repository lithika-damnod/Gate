import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-event-list',
  standalone: false,

  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent {
  @Input() event!: any;
}
