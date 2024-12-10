import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-auth',
  standalone: false,
  templateUrl: './auth.component.html',
})
export class AuthComponent {
  View = View;
  view: View = View.EMAIL;
  back?: View;
  form!: FormGroup;

  constructor(private authService: AuthService, private router: Router) {
    this.form = this.authService.createAuthForm();
  }

  navigateToView(view: View) {
    this.view = view;
  }

  submit(type: string): void {
    switch (type) {
      case "email":
        this.handleEmailSubmission();
        break;
      case "password":
        this.handlePasswordSubmission();
        break;
      case "create":
        this.handleCreateAccountSubmission();
        break;
    }
  }

  private async handleEmailSubmission(): Promise<void> {
    let email: string = this.form.get("email")?.value;
    const isAccountPresent = await this.authService.isAccountPresent(email);

    if (isAccountPresent) this.navigateToView(View.PASSWORD);
    else this.navigateToView(View.CREATE_ACCOUNT);
  }

  private async handlePasswordSubmission(): Promise<void> {
    let email: string = this.form.get("email")?.value;
    let password: string = this.form.get("password")?.value;
    const authenticated = await this.authService.authenticate(email, password);

    if (authenticated) this.router.navigate([""]);
    else alert("authentication failed");
  }

  private handleCreateAccountSubmission(): void {
    let email: string = this.form.get("email")?.value;
    let password: string = this.form.get("new_password")?.value;
    let firstname: string = this.form.get("first_name")?.value;
    let lastname: string = this.form.get("last_name")?.value;

    let account: boolean = this.authService.createAccount(email, password, firstname, lastname);
    if (account) this.router.navigate([""]);
    else alert("failed to create account");
  }
}

enum View {
  EMAIL = 1,
  PASSWORD,
  CREATE_ACCOUNT,
  SET_PASSWORD,
}

