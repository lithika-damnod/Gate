import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { environment } from '../../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom, Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private fb: FormBuilder, private http: HttpClient) { }

  createAuthForm(): FormGroup {
    return this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      new_password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  private apiUrl: string = environment.API_URL;

  async isAccountPresent(email: string): Promise<boolean> {
    try {
      const response = await lastValueFrom(
        this.http.get(`${this.apiUrl}/api/account?email=${email}`).pipe(
          catchError((error) => {
            console.log(error);
            throw error;
          })
        )
      );
      return true;
    } catch (error) {
      return false; // Return false if an error occurs
    }
  }

  async authenticate(email: string, password: string): Promise<boolean> {
    try {
      const response = await lastValueFrom(
        this.http.post(`${this.apiUrl}/api/auth/login`, { email: email, password: password }).pipe(
          catchError((error) => {
            console.log(error);
            throw error;
          })
        )
      );
      let token: string = (response as { token: string }).token;
      sessionStorage.setItem('accessToken', token);
      return true;
    } catch (error) {
      return false; // Return false if an error occurs
    }
  }

  async createAccount(email: string, password: string, first_name: string, last_name: string): Promise<boolean> {
    try {
      const response = await lastValueFrom(
        this.http.post(`${this.apiUrl}/api/auth/register`, { email: email, password: password, first_name: first_name, last_name: last_name }).pipe(
          catchError((error) => {
            console.log(error);
            throw error;
          })
        )
      );
      let token: string = (response as { token: string }).token;
      sessionStorage.setItem('accessToken', token);
      return true;
    } catch (error) {
      return false; // Return false if an error occurs
    }
  }
}
