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
@Component({
  selector: 'posts',
  standalone: true,
  imports: [CommonModule, Post, CustomButton],
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class Posts implements OnInit {
  posts: PostResponse[] = [];
  user: User;
  templatePosts: TemplatePost[] = [];
  limit = 10;
  offset = 0;

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
        // Fetch the user feed.
        this.userService
          .getUserFeed(this.user.id, this.limit, this.offset)
          .subscribe({
            next: (posts) => {
              this.posts = posts;

              // Mapping process from Post to TemplatePost.
              for (let post of this.posts) {
                // Get author name for the post.
                this.userService.getUser(post.user).subscribe({
                  next: (user) => {
                    let templatePost: TemplatePost = {
                      id: post.id,
                      title: post.title,
                      content: post.content,
                      date: new Date(post.updatedAt).toLocaleDateString(),
                      author: user.name,
                      topic: 'No further call needed here (property unused).',
                    };
                    this.templatePosts.push(templatePost);
                  },
                });
              }
            },
          });
      },
    });
  }

  incrementpage() {
    this.offset += 10;
  }

  decrementpage() {
    this.offset -= 10;
  }

  goToCreatePost() {
    this.router.navigate(['/create-post']);
  }

  desc: boolean = true;
  sortTemplatePosts() {
    this.templatePosts.sort((a, b) => {
      this.desc = !this.desc;
      if (this.desc) {
        return a.date > b.date ? 1 : -1;
      }
      return a.date < b.date ? 1 : -1;
    });
  }
}
