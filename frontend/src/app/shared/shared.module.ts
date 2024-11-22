import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconsModule } from './components/icons/icons.module';
import { EventCardComponent } from './components/event-card/event-card.component';
import { TimerComponent } from "./index"

@NgModule({
  declarations: [
    EventCardComponent,
    TimerComponent
  ],
  imports: [
    CommonModule,
    IconsModule
  ],
  exports: [IconsModule, EventCardComponent, TimerComponent]
})
export class SharedModule { }
