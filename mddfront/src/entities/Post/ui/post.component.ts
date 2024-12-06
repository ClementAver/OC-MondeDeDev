import { Component, Input } from '@angular/core';
import { TemplatePost } from '../model/TemplatePost.interface';

@Component({
  selector: 'post',
  standalone: true,
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class Post {
  @Input() post: TemplatePost;

  constructor() {
    this.post = {
      id: -1,
      title: '',
      content: '',
      date: new Date().toString(),
      author: '',
    };
  }
}
