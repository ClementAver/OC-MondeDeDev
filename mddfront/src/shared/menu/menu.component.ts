import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'menu',
  standalone: true,
  imports: [CommonModule],
  providers: [Router, RouterLink, RouterLinkActive],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class Menu {
  classes = {
    hidden: false,
  };

  constructor(private router: Router) {}

  toggleDisplay() {
    this.classes = { ...this.classes, hidden: !this.classes.hidden };
  }

  goConnect() {
    this.router.navigate(['/connect']);
  }

  ngOnInit() {
    window.addEventListener('resize', () => {
      if (window.innerWidth > 768) {
        this.classes = { ...this.classes, hidden: false };
      }
    });
  }
}
