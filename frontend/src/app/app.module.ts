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
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { FormatDatePipe } from './shared/pipes/format-date.pipe';
import { CapitalizePipe } from './shared/pipes/capitalize.pipe';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EventComponent,
    AuthComponent,
    MainLayoutComponent,
    BlankLayoutComponent,
    FormatDatePipe,
    CapitalizePipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    FeaturesModule,
    SharedModule,
    ReactiveFormsModule,
  ],
  providers: [provideHttpClient(withInterceptors([
    (req, next) => {
      const token = sessionStorage.getItem('accessToken');
      if (token) {
        const authReq = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${token}`),
        });
        return next(authReq);
      }
      return next(req);
    }
  ]))],
  bootstrap: [AppComponent]
})
export class AppModule { }
