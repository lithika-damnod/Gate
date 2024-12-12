import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-log',
  standalone: false,

  templateUrl: './log.component.html',
  styleUrl: './log.component.css'
})
export class LogComponent {
  @Input() type: "BUY" | "RELEASE" = "BUY";
  @Input() ticketType!: string;
  @Input() eventName!: string;
  @Input() numberOfReleasedTickets?: number;
}
