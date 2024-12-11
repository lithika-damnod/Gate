import { Component } from '@angular/core';

@Component({
    selector: 'app-hero-search',
    template: `
    <div class="w-full mb-20 mr-10">
        <h2 class="text-[1.1rem] font-medium text-[#ffffffe2]">Book Your Next Event in Seconds</h2>
        <div
            class="flex items-center justify-between border-y border-[#ffffff30] mt-3 p-1.5 w-full focus:outline-none">
            <div class="w-full flex items-center text-[#ffffffcc]">
                <span class="scale-[0.68] mr-[0.4rem]">
                    <icon-search />
                </span>
                <input class="w-full bg-black focus:outline-none text-[0.82rem] placeholder:text-[#ffffffcc]"
                    placeholder="Search for an Event, Movie, Webinar" />
            </div>
            <div class="flex items-center gap-3 ml-2">
                <button>
                    <div class="scale-[0.58] text-[#ffffff99]">
                        <icon-calendar />
                    </div>
                </button>
                <div class="border-[0.7px] border-[#ffffff30] h-3"></div>
                <button>
                    <div class="scale-[0.82] text-[#ffffffe2]">
                        <icon-right-arrow />
                    </div>
                </button>
            </div>
        </div>
    </div>
  `,
    standalone: false
})
export class HeroSearchComponent { }
