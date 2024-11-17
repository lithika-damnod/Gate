import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { SearchComponent } from './search/search.component';



@NgModule({
  declarations: [LogoComponent, SearchComponent],
  imports: [
    CommonModule
  ],
  exports: [LogoComponent, SearchComponent]
})
export class IconsModule { }
