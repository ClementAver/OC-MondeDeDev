import { Component, ViewEncapsulation, Input } from '@angular/core';

@Component({
  selector: 'custom-button',
  standalone: true,
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class CustomButton {
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() text: string = '';
  @Input() classes: {
    outlined?: boolean;
    disabled?: boolean;
  } = {
    outlined: false,
    disabled: false,
  };
  @Input() action: Function = () => {};

  switchEnability() {
    this.classes.disabled = !this.classes.disabled;
  }
}
