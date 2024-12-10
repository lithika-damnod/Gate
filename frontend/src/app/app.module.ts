import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { FeaturesModule } from './features/features.module';
import { SharedModule } from "./shared/shared.module";
import { HomeComponent } from './pages/home/home.component';
import { EventComponent } from './pages/event/event.component';
import { AuthComponent } from './pages/auth/auth.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { BlankLayoutComponent } from './layouts/blank-layout/blank-layout.component';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { FormatDatePipe } from './shared/pipes/format-date.pipe';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EventComponent,
    AuthComponent,
    MainLayoutComponent,
    BlankLayoutComponent,
    FormatDatePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    FeaturesModule,
    SharedModule,
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
