import { Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'icon-calendar',
    template: `
    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path fill-rule="evenodd" clip-rule="evenodd" d="M7.5 1C8.05228 1 8.5 1.44772 8.5 2V3H15.5V2C15.5 1.44772 15.9477 1 16.5 1C17.0523 1 17.5 1.44772 17.5 2V3.02469C20.0267 3.27555 22 5.40733 22 8V17C22 19.7614 19.7614 22 17 22H7C4.23858 22 2 19.7614 2 17V8C2 5.40733 3.97334 3.27555 6.5 3.02469V2C6.5 1.44772 6.94772 1 7.5 1ZM4.17071 7H19.8293C19.4175 5.83481 18.3062 5 17 5H7C5.69378 5 4.58254 5.83481 4.17071 7ZM20 9H4V17C4 18.6569 5.34315 20 7 20H17C18.6569 20 20 18.6569 20 17V9Z" fill="currentColor"/>
    </svg>
  `,
    encapsulation: ViewEncapsulation.None,
    standalone: false
})
export class CalendarComponent { }
