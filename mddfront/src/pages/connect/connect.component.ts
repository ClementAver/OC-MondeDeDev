import { Component } from '@angular/core';
import { CustomButton } from '../../shared/button/button.component';

@Component({
  selector: 'connect',
  standalone: true,
  imports: [CustomButton],
  templateUrl: './connect.component.html',
  styleUrl: './connect.component.scss',
})
export class Connect {}
