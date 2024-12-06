import { Component, Input } from '@angular/core';
import { RouterModule, RouterLink } from '@angular/router';

@Component({
  selector: 'back-button',
  standalone: true,
  imports: [RouterModule, RouterLink],
  templateUrl: './backButton.component.html',
  styleUrls: ['./backButton.component.scss'],
})
export class BackButton {
  @Input() route: string = '';
}
