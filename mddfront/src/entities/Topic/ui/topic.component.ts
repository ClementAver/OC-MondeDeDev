import { Component, Input } from '@angular/core';
import { CustomButton } from '../../../shared/button/button.component';
import { TemplateTopic } from '../model/TemplateTopic.interface';
import { TopicService } from '../api/TopicService';

@Component({
  selector: 'topic',
  imports: [CustomButton],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss'],
  standalone: true,
})
export class Topic {
  @Input() userId: number;
  @Input() topic: TemplateTopic;

  constructor(private topicService: TopicService) {
    this.userId = -1;
    this.topic = {
      id: -1,
      name: '',
      description: '',
      subscribed: false,
      createdAt: new Date().toString(),
      updatedAt: new Date().toString(),
    };
  }

  toggleSubscription(topic: TemplateTopic, id: number) {
    console.log('1');
    if (topic && id) {
      if (topic.subscribed) {
        console.log('2');
        this.topicService.unsubscribe(topic.id, id).subscribe({
          next: () => {
            console.log('3');
            topic.subscribed = false;
          },
          error: (err) => {
            console.error('Error unsubscribing from topic', err);
          },
        });
      } else {
        console.log('2');
        this.topicService.subscribe(topic.id, id).subscribe({
          next: () => {
            console.log('3');
            topic.subscribed = true;
          },
          error: (err) => {
            console.error('Error subscribing to topic', err);
          },
        });
      }
    }
  }
}
