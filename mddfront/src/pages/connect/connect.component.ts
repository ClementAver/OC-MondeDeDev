import { Component } from '@angular/core';
import { CustomButton } from '../../shared/button/button.component';
import { RouterLink  } from '@angular/router';

@Component({
  selector: 'connect',
  standalone: true,
  imports: [CustomButton, RouterLink ],
  templateUrl: './connect.component.html',
  styleUrls: ['./connect.component.scss'],
})
export class Connect {}
