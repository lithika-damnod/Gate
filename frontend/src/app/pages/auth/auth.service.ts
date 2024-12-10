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

  /*
  isAccountPresent(email: string): Observable<boolean> {
    console.log("email: ", email);

    return this.http.get(`${this.apiUrl}/api/account?email=${email}`).pipe(
      map((response) => {
        console.log(response);
        return true;
      }),
      catchError((error) => {
        console.log(error);
        return of(false);
      })
    );
  }
  */


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

  authenticate(email: string, password: string): boolean {
    // TODO: Write the logic here...
    return true;
  }

  createAccount(email: string, password: string, first_name: string, last_name: string): boolean {
    // TODO: Write the logic here...
    return true;
  }

  private initiateSession(): boolean {
    // TODO: Write the logic here...
    return true;
  }
}
