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
    targetDate!: Date;

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.id = params.get("id") ?? "";
        });

        this.event = this.eventService.getEvent(parseInt(this.id)).subscribe({
            next: (response) => {
                this.event = response;
                this.targetDate = new Date(this.event.time);
                console.log(this.targetDate);
            },
            error: (error) => {
                console.log("Error fetching events", error);
                this.router.navigate([""]); // redirect to the home page if some error occurs
            },
        });
    }

    selected?: number | null = null;
    totalPrice = 0;
    handleChecked = (checked: boolean, index: number) => {
        if (checked) {
            this.selected = index;
            this.totalPrice += this.event.ticket_types[index].price;
        }
        if (!checked) {
            this.selected = null;
            this.totalPrice -= this.event.ticket_types[index].price;
        }
    }

    handleProceeding(): void { // TODO:
        console.log("proceeding");
    }
}
