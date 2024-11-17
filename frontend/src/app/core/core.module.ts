import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from "../core/";
import { SharedModule } from "../shared/shared.module";



@NgModule({
  declarations: [NavigationComponent],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [NavigationComponent],
})
export class CoreModule { }
