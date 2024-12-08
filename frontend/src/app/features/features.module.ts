import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroSectionComponent } from './home/hero-section/hero-section.component';
import { SharedModule } from "../shared/shared.module";
import { IconsModule } from "../shared/components/icons/icons.module";
import { MainHighlightCardComponent } from './home/main-highlight-card/highlight-card.component';
import { SecondaryHighlightCardComponent } from './home/secondary-highlight-card/secondary-highlight-card.component';
import { HeroSearchComponent } from './home/hero-search/hero-search.component';
import { EventDetailsComponent } from './event/event-details/event-details.component';
import { TicketTypeListItemComponent } from './event/ticket-type-list-item/ticket-type-list-item.component';
import { InputComponent } from './auth/input/input.component';



@NgModule({
  declarations: [HeroSectionComponent, MainHighlightCardComponent, SecondaryHighlightCardComponent, HeroSearchComponent, EventDetailsComponent, TicketTypeListItemComponent, InputComponent],
  imports: [
    CommonModule,
    SharedModule,
    IconsModule
  ],
  exports: [HeroSectionComponent, MainHighlightCardComponent, SecondaryHighlightCardComponent, HeroSearchComponent, EventDetailsComponent, TicketTypeListItemComponent, InputComponent]

})
export class FeaturesModule { }
