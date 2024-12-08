import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthenticationService } from '../../../../features/authentication/api/AuthenticationService';
import { User } from '../../../../entities/User/model/User.interface';
import { CommentService } from '../../../../entities/Comment/api/CommentService';

@Component({
  selector: 'comment-create-form',
  standalone: true,
  templateUrl: './comment-create-form.component.html',
  imports: [ReactiveFormsModule, CommonModule],
  styleUrls: ['./comment-create-form.component.scss'],
})
export class CommentCreateForm implements OnInit {
  @Input() post = -1;

  user: User = {
    id: -1,
    email: '',
    name: '',
    createdAt: '',
    updatedAt: '',
  };

  constructor(
    private authenticationService: AuthenticationService,
    private commentService: CommentService
  ) {}

  createCommentForm = new FormGroup({
    content: new FormControl('', [
      Validators.required,
      Validators.maxLength(256),
    ]),
  });

  get content() {
    return this.createCommentForm.get('content');
  }

  ngOnInit(): void {
    this.authenticationService.me().subscribe({
      next: (user) => {
        this.user = user;
      },
    });
  }

  handleSubmit() {
    this.commentService
      .createComment(
        this.createCommentForm.value.content as string,
        this.post,
        this.user.id
      )
      .subscribe({
        next: () => {
          this.createCommentForm.reset();
          window.location.reload();
        },
      });
  }
}
