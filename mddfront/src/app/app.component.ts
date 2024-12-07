import { Component } from '@angular/core';
import {
  Router,
  NavigationEnd,
  RouterOutlet,
} from '@angular/router';
import { Menu } from '../shared/menu/menu.component';
import { CommonModule } from '@angular/common';
import { environment } from '../shared/config/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ Menu, CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = environment.title;
  menuIsVisible = true;
  navIsVisible = true;

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        (event as NavigationEnd).urlAfterRedirects !== '/connect'
          ? (this.menuIsVisible = true)
          : (this.menuIsVisible = false);
        (event as NavigationEnd).urlAfterRedirects !== '/login' &&
        (event as NavigationEnd).urlAfterRedirects !== '/register'
          ? (this.navIsVisible = true)
          : (this.navIsVisible = false);
      }
    });
  }
}
