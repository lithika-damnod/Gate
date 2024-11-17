import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './components/icons/logo/logo.component';
import { IconsModule } from './components/icons/icons.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    IconsModule
  ],
  exports: [IconsModule]
})
export class SharedModule { }
