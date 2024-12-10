import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-event-details',
    templateUrl: './event-details.component.html',
    standalone: false
})
export class EventDetailsComponent {
    @Input() name = "";
    @Input() category = "";
    @Input() description = "";
    @Input() venue = "";
    @Input() time = "";
}
