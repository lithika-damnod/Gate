import { EventsService } from './../../shared/services/events.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrl: './home.component.css',
    standalone: false
})
export class HomeComponent implements OnInit {
    constructor(private eventService: EventsService) { }

    events: any;
    ngOnInit(): void {
        this.events = this.eventService.getEvents().subscribe({
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
