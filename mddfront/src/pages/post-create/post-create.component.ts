import { Component } from '@angular/core';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';
import { PostCreateForm } from './components/post-create-form.component';

@Component({
  selector: 'app-post-create',
  standalone: true,
  imports: [BackButton, PostCreateForm],
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss'],
})
export class PostCreate {
  constructor() {}

  ngOnInit(): void {
    // Initialization logic here
  }

  createPost(): void {
    // Logic to create a post
  }
}
