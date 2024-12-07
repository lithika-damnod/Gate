import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-timer',
    template: `
    <div class="flex items-center gap-5" *ngIf="size === 'large'">
        <div class="flex flex-col items-center">
            <p class="text-[0.95rem] font-bold">01</p>
            <p class="text-[0.75rem] font-[460]">Days</p>
        </div>
        <div class="border-[0.7px] border-[#ffffff30] h-5"></div>
        <div class="flex flex-col items-center">
            <p class="text-[0.95rem] font-bold">06</p>
            <p class="text-[0.75rem] font-[460]">Hours</p>
        </div>
        <div class="border-[0.7px] border-[#ffffff30] h-5"></div>
        <div class="flex flex-col items-center">
            <p class="text-[0.95rem] font-bold">46</p>
            <p class="text-[0.75rem] font-[460]">Mins</p>
        </div>
        <div class="border-[0.7px] border-[#ffffff30] h-5"></div>
        <div class="flex flex-col items-center">
            <p class="text-[0.95rem] font-bold">02</p>
            <p class="text-[0.75rem] font-[460]">Secs</p>
        </div>
        <div class="border-[0.7px] border-[#ffffff30] h-5"></div>
    </div>
    <div class="text-[0.8rem] text-gray-200" *ngIf="size === 'small'">
        06 Hours | 24 Minutes | 31 Seconds
    </div>
  `,
    standalone: false
})
export class TimerComponent {
    @Input() size: string = "large";
}
