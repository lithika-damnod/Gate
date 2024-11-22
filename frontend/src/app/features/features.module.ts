import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroSectionComponent } from './home/hero-section/hero-section.component';
import { SharedModule } from "../shared/shared.module";
import { IconsModule } from "../shared/components/icons/icons.module";
import { MainHighlightCardComponent } from './home/main-highlight-card/highlight-card.component';
import { SecondaryHighlightCardComponent } from './home/secondary-highlight-card/secondary-highlight-card.component';
import { HeroSearchComponent } from './home/hero-search/hero-search.component';



@NgModule({
  declarations: [HeroSectionComponent, MainHighlightCardComponent, SecondaryHighlightCardComponent, HeroSearchComponent],
  imports: [
    CommonModule,
    SharedModule,
    IconsModule
  ],
  exports: [HeroSectionComponent, MainHighlightCardComponent, SecondaryHighlightCardComponent, HeroSearchComponent]

})
export class FeaturesModule { }
