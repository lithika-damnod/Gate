import { EventsService } from './../../shared/services/events.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-event',
    templateUrl: './event.component.html',
    styleUrl: './event.component.css',
    standalone: false
})
export class EventComponent implements OnInit {
    constructor(private eventService: EventsService, private route: ActivatedRoute, private router: Router) { }
    id: string = "";
    event: any;
    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.id = params.get("id") ?? "";
        });

        this.event = this.eventService.getEvent(parseInt(this.id)).subscribe({
            next: (response) => {
                this.event = response;
                console.log(this.event)
            },
            error: (error) => {
                console.log("Error fetching events", error);
                this.router.navigate([""]); // redirect to the home page if some error occurs
            },
        });
    }
}
