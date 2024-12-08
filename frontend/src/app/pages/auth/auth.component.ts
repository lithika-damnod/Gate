import { Component } from '@angular/core';


@Component({
  selector: 'app-auth',
  standalone: false,
  templateUrl: './auth.component.html',
})
export class AuthComponent {
  View = View;
  view: View = View.EMAIL;
  back?: View;

  navigateToView(view: View) {
    this.view = view;
  }
}

enum View {
  EMAIL = 1,
  PASSWORD,
  CREATE_ACCOUNT,
  SET_PASSWORD,
}