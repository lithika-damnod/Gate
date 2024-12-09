import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconsModule } from './components/icons/icons.module';
import { EventCardComponent } from './components/event-card/event-card.component';
import { TimerComponent, ProgressComponent } from "./index";
import { InputComponent } from './components/input/input.component';

@NgModule({
  declarations: [
    EventCardComponent,
    TimerComponent,
    ProgressComponent,
    InputComponent,
  ],
  imports: [
    CommonModule,
    IconsModule
  ],
  exports: [IconsModule, EventCardComponent, TimerComponent, ProgressComponent, InputComponent]
})
export class SharedModule { }
