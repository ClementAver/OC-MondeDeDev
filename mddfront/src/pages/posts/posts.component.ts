import { Component, OnInit } from '@angular/core';
import { Post } from '../../entities/Post/ui/post.component';
import { Post as PostResponse } from '../../entities/Post/model/Post.interface';
import { TemplatePost } from '../../entities/Post/model/TemplatePost.interface';
import { User } from '../../entities/User/model/User.interface';
import { CommonModule } from '@angular/common';
import { UserService } from '../../entities/User/api/UserService';
import { AuthenticationService } from '../../features/authentication/api/AuthenticationService';
import { CustomButton } from '../../shared/button/button.component';
import { Router } from '@angular/router';
import { map, forkJoin } from 'rxjs';
@Component({
  selector: 'posts',
  standalone: true,
  imports: [CommonModule, Post, CustomButton],
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class Posts implements OnInit {
  user: User;
  posts: PostResponse[] = [];
  templatePosts: TemplatePost[] = [];
  limit = 9;
  offset = 0;
  desc: boolean = true;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private router: Router
  ) {
    this.user = {
      id: -1,
      name: '',
      email: '',
      createdAt: new Date().toLocaleDateString(),
      updatedAt: new Date().toLocaleDateString(),
    };
  }

  ngOnInit(): void {
    // Fetch the user.
    this.authenticationService.me().subscribe({
      next: (user) => {
        this.user = user;
        this.getFeedPage();
      },
    });
  }

  getFeedPage() {
    // Fetch the user feed.
    this.userService
      .getUserFeed(this.user.id, this.limit, this.offset, this.desc)
      .subscribe({
        next: (posts) => {
          if (!posts || posts.length === 0) return;

          this.posts = posts;

          // Mapping process from Post to TemplatePost.
          const templatePostObservables = posts.map((post) =>
            // Get author name for the post.
            this.userService.getUser(post.user).pipe(
              map((user) => ({
                id: post.id,
                title: post.title,
                content: post.content,
                date: new Date(post.updatedAt).toLocaleDateString(),
                author: user.name,
                topic: 'No further call needed here (property unused).',
              }))
            )
          );

          // Wait for all posts to be complete (get authors).
          forkJoin(templatePostObservables).subscribe({
            next: (templatePosts) => {
              this.templatePosts = templatePosts;
            },
          });
        },
      });
  }

  incrementPage() {
    if (this.offset <= this.limit) {
      this.offset += 9;
      this.getFeedPage();
    }
  }

  decrementPage() {
    if (this.offset >= this.limit) {
      this.offset -= 9;
      this.getFeedPage();
    }
  }

  sortTemplatePosts() {
    this.desc = !this.desc;
    this.getFeedPage();
  }

  goToCreatePost() {
    this.router.navigate(['/create-post']);
  }
}
