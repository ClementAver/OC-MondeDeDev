import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpdateUserFormComponent } from './components/update-user-form/update-user-form.component';
import { CustomButton } from '../../shared/button/button.component';
import { AuthenticationService } from '../../features/authentication/api/AuthenticationService';
import { Topic } from '../../entities/Topic/ui/topic.component';
import { TemplateTopics } from '../../entities/Topic/model/TemplateTopics.interface';
import { UserService } from '../../entities/User/api/UserService';
import { User } from '../../entities/User/model/User.interface';

@Component({
  selector: 'profile',
  standalone: true,
  imports: [UpdateUserFormComponent, CustomButton, Topic, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class Profile {
  user: User;
  userTopics: TemplateTopics[] = [];
  constructor(
    private authenticationService: AuthenticationService,
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
    this.authenticationService.me().subscribe({
      next: (user) => {
        this.user = user;
        this.userService.getUserTopics(this.user.id).subscribe({
          next: (userTopics) => {
            this.userTopics = userTopics.map((topic) => ({
              ...topic,
              subscribed: true,
            }));
          },
        });
      },
    });
  }

  handleLogout() {
    this.authenticationService.logout();
  }
}
