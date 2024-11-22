import { Component, inject, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'back-button',
  standalone: true,
  providers: [Router],
  templateUrl: './backButton.component.html',
  styleUrls: ['./backButton.component.scss'],
})
export class BackButton {
  @Input() route: string = '';

  constructor(private router: Router) {}

  handleClick() {
    this.router.navigate([this.route]);
  }
}
