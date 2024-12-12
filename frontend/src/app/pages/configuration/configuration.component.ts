import { WebSocketService } from './../../core/services/web-socket.service';
import { EventsService } from './../../shared/services/events.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-configuration',
  standalone: false,

  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})
export class ConfigurationComponent implements OnInit {
  constructor(private eventService: EventsService, private webSocketService: WebSocketService) { }

  messages: any[] = [];

  events: any;
  ngOnInit(): void {
    this.events = this.eventService.getEvent().subscribe({
      next: (response) => {
        this.events = response;
      },
      error: (error) => {
        console.log("Error fetching events", error);
      },
    });


    this.webSocketService.getMessages().subscribe(message => {
      // in here we only consider messages with 'release' or 'buy'
      this.reflect(message);
    });
  }

  // TODO: 
  private reflect(message: any): void {
    return;
    /*
    switch (Object.keys(message)[0]) {
      case "release":
        break;
      case "buy":
        const eventId = this.messages.buy.eventId;
        const typeId = this.messages.buy.ticketTypeId;
        break;
    }
    */
  }

}
