import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { SearchComponent } from './search/search.component';
import { CalendarComponent } from './calendar/calendar.component';
import { RightArrowComponent } from './right-arrow/right-arrow.component';
import { EyeComponent } from './eye/eye.component';
import { CloseComponent } from './close/close.component';



@NgModule({
  declarations: [LogoComponent, SearchComponent, CalendarComponent, RightArrowComponent, EyeComponent, CloseComponent],
  imports: [
    CommonModule
  ],
  exports: [LogoComponent, SearchComponent, CalendarComponent, RightArrowComponent, EyeComponent, CloseComponent]
})
export class IconsModule { }
