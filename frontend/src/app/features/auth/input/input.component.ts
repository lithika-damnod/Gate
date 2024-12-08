import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-input',
  standalone: false,
  template: `
    <ng-container *ngIf="type === 'text'">
      <input type="text" 
          class="w-[320px] p-2 px-4 border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] placeholder:text-[#ffffffb3] focus:outline-[#015ECC]"
          [placeholder]="placeholder" />
    </ng-container>

    <ng-container *ngIf="type === 'password'">
      <input type="password" 
          class="w-[320px] p-2 px-4 border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] placeholder:text-[#ffffffb3] focus:outline-[#015ECC]"
          [placeholder]="placeholder" />
      <div class="mt-3.5 flex w-full h-[3px] gap-2 px-1 mt-" *ngIf="strength">
          <div class="w-full h-full bg-[#f0f2a3b3] flex-shrink"></div>
          <div class="w-full h-full bg-[#f0f2a3b3] flex-shrink"></div>
          <div class="w-full h-full bg-[#f0f2a3b3] flex-shrink"></div>
          <div class="w-full h-full bg-[#f0f2a3b3] flex-shrink"></div>
          <div class="w-full h-full bg-[#f0f2a3b3] flex-shrink"></div>
      </div>

    </ng-container>
  `,
})
export class InputComponent {
  @Input() type!: "text" | "password";
  @Input() strength?: boolean = false;
  @Input() placeholder!: string;
}
