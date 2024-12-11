import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environment/environment';
import { lastValueFrom } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TicketsService {
  private apiUrl: string = environment.API_URL;

  constructor(private http: HttpClient, private router: Router) { }

  async bookTicket(event_id?: number, ticket_type_id?: number): Promise<any> {
    try {
      const response = await lastValueFrom(
        this.http.post(`${this.apiUrl}/api/ticket`, { event_id, ticket_type_id }).pipe(
          catchError((error) => {
            console.log(error);
            throw error;
          })
        )
      );
      return response;
    } catch (error: any) {
      if (error.status === 401) {
        this.router.navigate(['/auth']);
      }
      else if (error.status === 403) {
        console.log("ticket type is no longer available", error);
      }
      else {
        console.log("error occured while booking ticket", error);
      }
      return null;
    }
  }
}
