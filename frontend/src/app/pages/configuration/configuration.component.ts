import { EventsService } from './../../shared/services/events.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-configuration',
  standalone: false,

  templateUrl: './configuration.component.html',
  styleUrl: './configuration.component.css'
})
export class ConfigurationComponent implements OnInit {
  constructor(private eventService: EventsService) { }

  events: any;
  ngOnInit(): void {
    this.events = this.eventService.getEvent().subscribe({
      next: (response) => {
        this.events = response;
        console.log(this.events);
      },
      error: (error) => {
        console.log("Error fetching events", error);
      },
    });
  }
}
