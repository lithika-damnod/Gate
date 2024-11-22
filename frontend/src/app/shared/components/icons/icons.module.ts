import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { SearchComponent } from './search/search.component';
import { CalendarComponent } from './calendar/calendar.component';
import { RightArrowComponent } from './right-arrow/right-arrow.component';



@NgModule({
  declarations: [LogoComponent, SearchComponent, CalendarComponent, RightArrowComponent],
  imports: [
    CommonModule
  ],
  exports: [LogoComponent, SearchComponent, CalendarComponent, RightArrowComponent]
})
export class IconsModule { }
