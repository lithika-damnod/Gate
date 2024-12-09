import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-progress',
    template: `
    <div class="w-[100px] h-[4px] bg-[#ffffff9c] rounded-lg overflow-hidden">
      <div class="h-full bg-[#cc55558a]" [ngStyle]="{'width': (progress + '%'), 'background-color': getProgressColor()}"></div>
    </div>
  `,
    standalone: false
})
export class ProgressComponent {
  @Input() progress!: number;
  @Input() color?: string = "#ffffff9c";

  getProgressColor(): string {
    if (this.progress <= 65) {
      return '#ffffffab';
    } else {
      return '#cc55558a';
    }
  }
}
