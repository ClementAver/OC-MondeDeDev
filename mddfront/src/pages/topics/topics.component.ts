import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Topic } from '../../entities/Topic/ui/topic.component';
import { TopicService } from '../../entities/Topic/api/TopicService';
import { Topic as TopicResponse } from '../../entities/Topic/model/Topic.interface';
import { UserService } from '../../entities/User/api/UserService';
import { AuthenticationService } from '../../features/authentication/api/AuthenticationService';
import { User } from '../../entities/User/model/User.interface';
import { TemplateTopic } from '../../entities/Topic/model/TemplateTopic.interface';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'topics',
  templateUrl: './topics.component.html',
  imports: [CommonModule, Topic],
  styleUrls: ['./topics.component.scss'],
  standalone: true,
})
export class Topics implements OnInit {
  topics: TopicResponse[] = [];
  user: User;
  userTopics: TopicResponse[] = [];
  templateTopic: TemplateTopic[] = [];

  constructor(
    private authenticationService: AuthenticationService,
    private topicService: TopicService,
    private userService: UserService
  ) {
    this.user = {
      id: -1,
      name: '',
      email: '',
      createdAt: new Date().toString(),
      updatedAt: new Date().toString(),
    };
  }

  ngOnInit(): void {
    // Fetch the authenticated user and all topics concurrently.
    forkJoin({
      user: this.authenticationService.me(),
      topics: this.topicService.getTopics(),
    }).subscribe({
      next: ({ user, topics }) => {
        this.user = user;
        this.topics = topics;

        // Fetch the authenticated user's topics after user data is available.
        if (this.user) {
          this.userService.getUserTopics(this.user.id).subscribe({
            next: (userTopics) => {
              this.userTopics = userTopics;

              // Process the topics after user topics are available.
              for (let topic of this.topics) {
                let templateTopic: TemplateTopic = {
                  ...topic,
                  subscribed: this.userTopics.some(
                    (userTopic) => userTopic.id === topic.id
                  ),
                };
                this.templateTopic.push(templateTopic);
              }
            },
            error: (error) => {
              console.error('Error fetching user topics', error);
            },
          });
        }
      },
      error: (error) => {
        console.error('Error fetching user or topics', error);
      },
    });
  }
}
