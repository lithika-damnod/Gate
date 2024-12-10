import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: false,
  styleUrl: "./input.component.css",
  template: `
    <ng-container *ngIf="type === 'text'"
      [formGroup]="form"
    >
      <input type="text" 
          class="w-[320px] p-2 px-4 border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] placeholder:text-[#ffffffb3] focus:outline-[#015ECC]"
          [placeholder]="placeholder" [formControlName]="formName" />
    </ng-container>

    <ng-container *ngIf="type === 'password'">
      <div 
        class="flex justify-between items-center password-input box-border w-[320px] border-[1.6px] border-[#ffffff26] bg-black rounded-lg text-[0.85rem] overflow-hidden"
        [formGroup]="form"
      >
        <input [type]="passwordSecondaryType" 
          class="overflow-hidden w-full p-2 px-4 text-[0.85rem] bg-black placeholder:text-[#ffffffb3] border-none outline-none flex-shrink"
          [placeholder]="placeholder"
          (input)="onInput($event)"
          [formControlName]="formName"
        />
        <button class="w-[28px] h-[24px] scale-75 mr-2" (click)="togglePasswordVisibility()">
          <app-eye [color]="color" /> 
        </button>
      </div>
      <div class="mt-3.5 flex w-full h-[3px] gap-2 px-1" *ngIf="strength">
          <div class="w-full h-full flex-shrink transition-colors"
            [ngStyle]="{'background-color': setScoreColor(1)}"
          ></div>
          <div class="w-full h-full flex-shrink transition-colors"
            [ngStyle]="{'background-color': setScoreColor(2)}"
          ></div>
          <div class="w-full h-full flex-shrink transition-colors"
            [ngStyle]="{'background-color': setScoreColor(3)}"
          ></div>
          <div class="w-full h-full flex-shrink transition-colors"
            [ngStyle]="{'background-color': setScoreColor(4)}"
          ></div>
          <div class="w-full h-full flex-shrink transition-colors"
            [ngStyle]="{'background-color': setScoreColor(5)}"
          ></div>
      </div>
    </ng-container>
  `,
})
export class InputComponent {
  @Input() type!: "text" | "password";
  @Input() strength?: boolean = false;
  @Input() placeholder!: string;
  @Input() formName!: string;
  @Input() form!: FormGroup;

  color: string = "white";
  passwordSecondaryType: string = "password";
  togglePasswordVisibility() {
    this.color = this.color === "white" ? "#fc9090" : "white";
    this.passwordSecondaryType = this.passwordSecondaryType === "password" ? "text" : "password";
  }


  score: number = 0;
  scoreColor: string = "#D9D9D9";
  onInput(event: Event) {
    this.score = 0;

    const element = event.target as HTMLInputElement;
    let password: string = element.value;
    if (password.length >= 8) this.score++; // Length of 8 or more
    if (/[a-z]/.test(password)) this.score++; // Contains lowercase
    if (/[A-Z]/.test(password)) this.score++; // Contains uppercase
    if (/\d/.test(password)) this.score++; // Contains number
    if (/[\W_]/.test(password)) this.score++; // Contains special character

    console.log("strength: ", this.score);
  }

  setScoreColor(index: number): string {
    switch (this.score) {
      case 0:
      default:
        return "#d9d9d9";
      case 1:
        return index <= 1 ? "#e39299" : "#D9D9D9";
      case 2:
        return index <= 2 ? "#f7aa83b3" : "#D9D9D9";
      case 3:
        return index <= 3 ? "#ffe093" : "#D9D9D9";
      case 4:
        return index <= 4 ? "#a9f08b" : "#D9D9D9";
      case 5:
        return index <= 5 ? "#78c17c" : "#D9D9D9";
    }
  }
}
