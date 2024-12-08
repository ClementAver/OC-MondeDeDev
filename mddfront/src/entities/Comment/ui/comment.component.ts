import { Component, Input } from '@angular/core';
import { TemplateComment } from '../model/TemplateComment.interface';

@Component({
  selector: 'comment',
  standalone: true,
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
})
export class Comment {
  @Input() comment: TemplateComment = {
    author: '',
    content: '',
  };
}
