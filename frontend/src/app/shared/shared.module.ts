import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconsModule } from './components/icons/icons.module';
import { EventCardComponent } from './components/event-card/event-card.component';
import { TimerComponent, ProgressComponent } from "./index";

@NgModule({
  declarations: [
    EventCardComponent,
    TimerComponent,
    ProgressComponent
  ],
  imports: [
    CommonModule,
    IconsModule
  ],
  exports: [IconsModule, EventCardComponent, TimerComponent, ProgressComponent]
})
export class SharedModule { }
