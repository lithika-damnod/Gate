import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-input',
  standalone: false,
  styleUrl: "./input.component.css",
  template: `
    <ng-container *ngIf="type === 'text'">
      <input type="text" 
          class="w-[320px] p-2 px-4 border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] placeholder:text-[#ffffffb3] focus:outline-[#015ECC]"
          [placeholder]="placeholder" />
    </ng-container>

    <ng-container *ngIf="type === 'password'">
      <div 
        class="flex justify-between items-center password-input box-border w-[320px] border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] overflow-hidden"
      >
        <input [type]="passwordSecondaryType" 
            class="overflow-hidden w-full p-2 px-4 text-[0.85rem] bg-black placeholder:text-[#ffffffb3] border-none outline-none flex-shrink"
            [placeholder]="placeholder" />
        <button class="w-[28px] h-[24px] scale-75 mr-2" (click)="togglePasswordVisibility()">
          <app-eye [color]="color" /> 
        </button>
      </div>
      <div class="mt-3.5 flex w-full h-[3px] gap-2 px-1" *ngIf="strength">
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

  color: string = "white";
  passwordSecondaryType: string = "password";
  togglePasswordVisibility() {
    this.color = this.color === "white" ? "#fc9090" : "white";
    this.passwordSecondaryType = this.passwordSecondaryType === "password" ? "text" : "password";
  }
}
