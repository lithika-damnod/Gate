import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket!: WebSocket;
  private subject = new Subject<any>();
  private url = `${environment.API_URL}/ws/stream`;

  connect(): void {
    if (!this.socket || this.socket.readyState !== WebSocket.OPEN) {
      this.socket = new WebSocket(this.url);
      this.socket.onopen = () => {
        console.log('websocket connection established.');
      };

      // listen to incoming messages
      this.socket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        this.subject.next(data);
      };

      // if an error is thrown
      this.socket.onerror = (event) => {
        console.error('websocket error:', event);
      };

      // if connection closes
      this.socket.onclose = () => {
        console.log('websocket connection closed.');
      };
    }
  }

  getMessages(): Observable<any> {
    return this.subject.asObservable();
  }

  close(): void {
    if (this.socket) {
      this.socket.close();
      console.log('websocket connection closed.');
    }
  }
}

