import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { CustomButton } from '../../../shared/button/button.component';
import { CustomInput } from '../../../shared/input/input.component';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { PostService } from '../../../entities/Post/api/PostService';
import { forkJoin } from 'rxjs';
import { TopicService } from '../../../entities/Topic/api/TopicService';

@Component({
  selector: 'post-create-form',
  standalone: true,
  imports: [ReactiveFormsModule, CustomInput, CustomButton, CommonModule],
  templateUrl: './post-create-form.component.html',
  styleUrls: ['./post-create-form.component.scss'],
})
export class PostCreateForm implements OnInit {
  postTitle: string = '';
  postContent: string = '';
  topics: { id: number; name: string }[] = [];
  isFalse: boolean = true;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private postService: PostService,
    private topicService: TopicService
  ) {}

  createPostForm = new FormGroup({
    topic: new FormControl(0, [Validators.required]),
    title: new FormControl('', [
      Validators.required,
      Validators.maxLength(256),
    ]),
    content: new FormControl('', [
      Validators.required,
      Validators.maxLength(8192),
    ]),
  });

  get topic() {
    return this.createPostForm.get('topic');
  }

  get title() {
    return this.createPostForm.get('title');
  }

  get content() {
    return this.createPostForm.get('content');
  }

  ngOnInit() {
    this.createPostForm.get('topic')?.valueChanges.subscribe((value) => {
      this.isFalse = value == 0;
    });

    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics.map((topic) => {
          return { id: topic.id, name: topic.name };
        });
      },
    });
  }

  handleSubmit() {
    let requestBody = {
      topic: this.createPostForm.value.topic,
      title: this.createPostForm.value.title,
      content: this.createPostForm.value.content,
      user: 0,
    };

    forkJoin({
      user: this.authenticationService.me(),
      topic: this.topicService.getTopic(requestBody.topic as number),
    }).subscribe({
      next: (result) => {
        requestBody.user = result.user.id;
        requestBody.topic = result.topic.id;

        this.postService
          .createPost(
            requestBody.title as string,
            requestBody.content as string,
            requestBody.topic as number,
            requestBody.user
          )
          .subscribe({
            next: () => {
              this.router.navigate(['/posts']);
            },
          });
      },
    });
  }
}
