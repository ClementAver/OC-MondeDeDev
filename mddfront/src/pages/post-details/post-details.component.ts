import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../entities/Post/api/PostService';
import { UserService } from '../../entities/User/api/UserService';
import { TopicService } from '../../entities/Topic/api/TopicService';
import { CommentService } from '../../entities/Comment/api/CommentService';
import { TemplatePost } from '../../entities/Post/model/TemplatePost.interface';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';
import { TemplateComment } from '../../entities/Comment/model/TemplateComment.interface';
import { Comment } from '../../entities/Comment/ui/comment.component';
import { CommentCreateForm } from './components/comment-create-form/comment-create-form.component';

@Component({
  selector: 'post-details',
  standalone: true,
  imports: [BackButton, Comment, CommonModule, CommentCreateForm],
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss'],
})
export class PostDetails implements OnInit {
  post: TemplatePost = {
    id: -1,
    title: '',
    content: '',
    date: new Date().toLocaleDateString(),
    author: '',
    topic: '',
  };
  comments: TemplateComment[] = [];

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private userService: UserService,
    private topicService: TopicService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = Number(params.get('id'));
      if (id) {
        this.postService.getPost(id).subscribe({
          next: (post) => {
            forkJoin({
              user: this.userService.getUser(post.user),
              topic: this.topicService.getTopic(post.topic),
              comments: this.commentService.getCommentsByPostId(post.id),
            }).subscribe({
              next: ({ user, topic, comments }) => {
                this.post = {
                  id: post.id,
                  title: post.title,
                  content: post.content,
                  date: new Date(post.updatedAt).toLocaleDateString(),
                  author: user.name,
                  topic: topic.name,
                };

                const commentObservables = comments.map((comment) =>
                  this.userService.getUser(comment.user).pipe(
                    map((user) => ({
                      author: user.name,
                      content: comment.content,
                    }))
                  )
                );

                forkJoin(commentObservables).subscribe({
                  next: (templateComments) => {
                    this.comments = templateComments;
                  },
                });
              },
            });
          },
        });
      }
    });
  }
}