import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../entities/Post/api/PostService';
import { UserService } from '../../entities/User/api/UserService';
import { TopicService } from '../../entities/Topic/api/TopicService';
import { TemplatePost } from '../../entities/Post/model/TemplatePost.interface';
import { forkJoin } from 'rxjs';
import { BackButton } from '../../widgets/backButton/ui/backButton.component';

@Component({
  selector: 'post-details',
  standalone: true,
  imports: [BackButton],
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

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private userService: UserService,
    private topicService: TopicService
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
            }).subscribe({
              next: ({ user, topic }) => {
                this.post = {
                  id: post.id,
                  title: post.title,
                  content: post.content,
                  date: new Date(post.updatedAt).toLocaleDateString(),
                  author: user.name,
                  topic: topic.name,
                };
              },
              error: (err) => {
                console.error(
                  "Error fetching post's details (author or topic):",
                  err
                );
              },
            });
          },
          error: (err) => {
            console.error('Error fetching post:', err);
          },
        });
      }
    });
  }
}
