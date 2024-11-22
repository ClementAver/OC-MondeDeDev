import { Component } from '@angular/core';
import { Router, RouterOutlet, NavigationEnd } from '@angular/router';
import { Menu } from '../shared/menu/menu.component';
import { CommonModule } from '@angular/common';
import { environment } from '../shared/config/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Menu, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = environment.title;
  menuDisplay: boolean = false;
  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        (event as NavigationEnd).urlAfterRedirects !== '/connect'
          ? (this.menuDisplay = true)
          : (this.menuDisplay = false);
      }
    });
  }
}
