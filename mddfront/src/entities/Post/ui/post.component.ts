import { Component, Input } from '@angular/core';
import { TemplatePost } from '../model/TemplatePost.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'post',
  standalone: true,
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class Post {
  @Input() post: TemplatePost;

  constructor(private router: Router) {
    this.post = {
      id: -1,
      title: '',
      content: '',
      date: new Date().toString(),
      author: '',
      topic: '',
    };
  }

  goToPost(id: number) {
    this.router.navigate(['/posts', id]);
  }
}
