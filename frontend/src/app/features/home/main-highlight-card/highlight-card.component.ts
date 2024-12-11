import { Component } from '@angular/core';

@Component({
    selector: 'app-main-highlight-card',
    template: `
        <div
            class="p-4 border-b-[1px] border-[#ffffff30] text-[#ffffffb3] hover:bg-[#ffffff12] transition-colors duration-300 cursor-pointer">
            <h5 class="text-[0.92rem] font-[465]">2024 Design Exhibition</h5>
            <h6 class="text-[0.73rem] font-[420]">Spencer Building, Bambalapitiya</h6>
            <p class="text-[0.73rem] mt-2.5 font-[420] w-[95%] leading-[1.2rem]">
                vienna-based collective gelitin filled the museum boijmans van beuningen in rotterdam with a series of
                monumental, site-specific sculptures shaped like giant turds.
            </p>
            <div class="flex justify-between items-center mt-8 pl-1">
                <app-timer></app-timer>
                <div>
                    <p class="text-[0.78rem] text-[#ffffff80] font-[450]">Fri Oct 18, 3:00PM</p>
                </div>
            </div>
        </div>
  `,
    standalone: false
})
export class MainHighlightCardComponent { }
