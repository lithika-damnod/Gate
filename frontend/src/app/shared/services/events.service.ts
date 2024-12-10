import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class EventsService {
  private apiUrl: string = environment.API_URL;

  constructor(private http: HttpClient) { }

  getEvent(id?: number): Observable<any> {
    return this.http.get(id ? `${this.apiUrl}/api/event/${id}` : `${this.apiUrl}/api/event`);
  }
}