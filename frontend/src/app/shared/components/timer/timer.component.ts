import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription, interval } from 'rxjs';

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
        {{this.timeRemaining.hours}} Hours | {{this.timeRemaining.minutes}} Minutes | {{this.timeRemaining.seconds}} Seconds
    </div>
  `,
    standalone: false
})
export class TimerComponent implements OnInit, OnDestroy {
    @Input() size: string = "large";
    @Input() targetDate!: Date; // Target date

    timeRemaining: { days: number, hours: number; minutes: number; seconds: number; } = {
        days: 0,
        hours: 0,
        minutes: 0,
        seconds: 0
    }

    private countdown$: Subscription | undefined;

    ngOnInit(): void {
        this.startCountDown();
    }
    startCountDown(): void {
        this.countdown$ = interval(1000).subscribe(() => {
            this.updateRemainingTime();
            console.log("time remaining: ", this.timeRemaining);
        });
    }

    updateRemainingTime(): void {
        const now = new Date().getTime();
        const target = this.targetDate.getTime();
        const difference = target - now;

        if (difference > 0) {
            this.timeRemaining = this.calculateTime(difference);
        } else {
            this.timeRemaining = { days: 0, hours: 0, minutes: 0, seconds: 0 };
            this.stopCountdown();
        }
    }

    calculateTime(difference: number): { days: number; hours: number; minutes: number; seconds: number } {
        const seconds = Math.floor((difference / 1000) % 60);
        const minutes = Math.floor((difference / (1000 * 60)) % 60);
        const hours = Math.floor((difference / (1000 * 60 * 60)) % 24);
        const days = Math.floor(difference / (1000 * 60 * 60 * 24));

        return { days, hours, minutes, seconds };
    }

    stopCountdown(): void {
        if (this.countdown$) {
            this.countdown$.unsubscribe();
        }
    }

    ngOnDestroy(): void {
        this.stopCountdown(); // clean subscription when the component is destroyed
    }
}
