import { Component } from '@angular/core';

@Component({
  selector: 'app-secondary-highlight-card',
  template: `
        <div
            class="flex justify-between items-center p-4 text-[#ffffffb3] hover:bg-[#ffffff12] transition-colors duration-300 cursor-pointer">
            <div>
                <h5 class="text-[0.92rem] font-[465]">London Tech Week</h5>
                <h6 class="text-[0.73rem] font-[420]">Event Technology, London</h6>
            </div>
            <div class="text-[0.92rem] font-[465]">
                5 Hours Left
            </div>
        </div>
  `,
})
export class SecondaryHighlightCardComponent { }
