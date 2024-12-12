import { WebSocketService } from './core/services/web-socket.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: false
})
export class AppComponent implements OnInit {
  title = 'frontend';
  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
    this.webSocketService.connect();
  }
}
