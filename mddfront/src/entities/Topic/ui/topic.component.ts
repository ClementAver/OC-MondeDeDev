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
    };
  }

  toggleSubscription(topic: TemplateTopic, id: number) {
    if (topic && id) {
      if (topic.subscribed) {
        this.topicService.unsubscribe(topic.id, id).subscribe({
          next: () => {
            topic.subscribed = false;
          },
          error: (err) => {
            console.error('Error unsubscribing from topic', err);
          },
        });
      } else {
        this.topicService.subscribe(topic.id, id).subscribe({
          next: () => {
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
