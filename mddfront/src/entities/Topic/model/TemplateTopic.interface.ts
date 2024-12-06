import { Topic } from './Topic.interface';

export interface TemplateTopic extends Topic {
  subscribed: boolean;
}
