import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { FeaturesModule } from './features/features.module';
import { SharedModule } from "./shared/shared.module";
import { HomeComponent } from './pages/home/home.component';
import { EventComponent } from './pages/event/event.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EventComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    FeaturesModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
