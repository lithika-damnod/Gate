import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
    selector: 'app-ticket-type-list-item',
    template: `
    <button
        class="flex justify-between items-center w-full p-4 border-b border-[#ffffff30] px-7 font-[400] text-[#d1d5dbec] ">
        <div class="flex items-center gap-5">
            <input type="checkbox" class="border-white" />
            <h3 class="text-[0.882rem]">{{ name }}</h3>
        </div>
        <div class="flex items-center gap-8 text-[0.855rem]">
            <div class="h-8 bg-[#ffffff30] w-[1.5px]"></div>
            <h4>{{ price }}</h4>
            <div class="h-8 bg-[#ffffff30] w-[1.5px]"></div>
            <app-progress [progress]="progress" />
        </div>
    </button>
  `,
    standalone: false
})
export class TicketTypeListItemComponent {
  @Input() name!: string;
  @Input() price!: string;
  @Input() progress!: number;
}
