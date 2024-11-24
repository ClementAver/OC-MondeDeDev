import { Component, Input } from '@angular/core';
import { Router, RouterModule, RouterLink } from '@angular/router';

@Component({
  selector: 'back-button',
  standalone: true,
  imports: [RouterModule, RouterLink],
  providers: [Router],
  templateUrl: './backButton.component.html',
  styleUrls: ['./backButton.component.scss'],
})
export class BackButton {
  @Input() route: string = '';
}
