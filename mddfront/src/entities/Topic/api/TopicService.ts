import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../shared/config/environment';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Topic as TopicResponse, Topic } from '../model/Topic.interface';

@Injectable({ providedIn: 'root' })
export class TopicService {
  private apiURL = environment.apiURL;

  constructor(
    private httpClient: HttpClient,
    private errorHandler: ErrorHandler,
    private authenticationService: AuthenticationService
  ) {}

  getTopics(): Observable<TopicResponse[]> {
    return this.httpClient
      .get<TopicResponse[]>(`${this.apiURL}/topic`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getTopic(id: number): Observable<TopicResponse> {
    return this.httpClient
      .get<TopicResponse>(`${this.apiURL}/topic/${id}`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  subscribe(topicId: number, userId: number): Observable<string> {
    return this.httpClient
      .post<string>(
        `${this.apiURL}/topic/${topicId}/subscribe/${userId}`,
        {},
        { headers: this.authenticationService.getHeaders(), responseType: 'text' as 'json' }
      )
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  unsubscribe(topicId: number, userId: number): Observable<string> {
    return this.httpClient
      .post<string>(
        `${this.apiURL}/topic/${topicId}/unsubscribe/${userId}`,
        {},
        { headers: this.authenticationService.getHeaders(), responseType: 'text' as 'json' }
      )
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}
