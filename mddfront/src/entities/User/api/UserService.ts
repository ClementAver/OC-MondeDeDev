import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../shared/config/environment';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User as UserResponse } from '../model/User.interface';
import { Topic } from '../../Topic/model/Topic.interface';
import { Post } from '../../Post/model/Post.interface';

@Injectable({ providedIn: 'root' })
export class UserService {
  private apiURL = environment.apiURL;

  constructor(
    private httpClient: HttpClient,
    private errorHandler: ErrorHandler,
    private authenticationService: AuthenticationService
  ) {}

  getUser(id: number): Observable<UserResponse> {
    return this.httpClient
      .get<UserResponse>(`${this.apiURL}/user/${id}`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getUserTopics(id: number): Observable<Topic[]> {
    return this.httpClient
      .get<Topic[]>(`${this.apiURL}/user/${id}/topics`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getUserFeed(
    id: number,
    limit: number,
    offset: number,
    sort: boolean
  ): Observable<Post[]> {
    return this.httpClient
      .post<Post[]>(
        `${this.apiURL}/user/${id}/feed`,
        { limit, offset, sort },
        { headers: this.authenticationService.getHeaders() }
      )
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getUserFeedSize(id: number): Observable<number> {
    return this.httpClient
      .post<number>(`${this.apiURL}/user/${id}/feed/size`, {},{
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}
