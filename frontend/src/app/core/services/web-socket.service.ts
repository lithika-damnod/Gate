import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from '../../../environment/environment';

interface WebSocketMessage {
  buy?: BuyMessage;
  release?: any;
  event?: any;
}
interface BuyMessage {
  eventId: number;
  eventName: string;
  ticketTypeId: number;
  ticketType: string;
}


@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket!: WebSocket;
  private subject = new Subject<WebSocketMessage>();
  private url = `${environment.API_URL}/ws/stream`;

  connect(): void {
    if (!this.socket || this.socket.readyState !== WebSocket.OPEN) {
      this.socket = new WebSocket(this.url);
      this.socket.onopen = () => {
        console.log('websocket connection established.');
      };

      // listen to incoming messages
      this.socket.onmessage = (event) => {
        try {
          const data: WebSocketMessage = JSON.parse(event.data);
          this.subject.next(data);
        }
        catch (error) {
          console.error('Error parsing WebSocket message:', error);
        }
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

  getMessages(): Observable<WebSocketMessage> {
    return this.subject.asObservable();
  }

  close(): void {
    if (this.socket) {
      this.socket.close();
      console.log('websocket connection closed.');
    }
  }
}
