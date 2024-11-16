import { Component, inject } from '@angular/core';
import { NavigationService } from '../model/NavigationService';

@Component({
  selector: 'back-button',
  standalone: true,
  templateUrl: './backButton.component.html',
  styleUrls: ['./backButton.component.scss'],
})
export class BackButton {
  private navigation = inject(NavigationService);

  handleClick() {
    this.navigation.back();
  }
}
