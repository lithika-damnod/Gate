import { Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'icon-right-arrow',
    template: `
    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path d="M6 12H18.5M18.5 12L12.5 6M18.5 12L12.5 18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
  `,
    encapsulation: ViewEncapsulation.None,
    standalone: false
})
export class RightArrowComponent { }
