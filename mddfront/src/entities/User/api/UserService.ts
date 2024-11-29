
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  private getHeaders(): HttpHeaders {
    const accessToken = this.authenticationService.getAccessToken();
    if (!accessToken) {
      throw new Error('Access token is not set');
    }
    return new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });
  }

  getUser(id: number): Observable<UserResponse> {
    return this.httpClient
      .get<UserResponse>(`${this.apiURL}/user/${id}`, { headers: this.getHeaders() })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getUserTopics(id: number): Observable<Topic[]> {
    return this.httpClient
      .get<Topic[]>(`${this.apiURL}/user/${id}/topics`, { headers: this.getHeaders() })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getUserFeed(id: number, limit: number, offset: number): Observable<Post[]> {
    return this.httpClient
      .get<Post[]>(`${this.apiURL}/user/${id}/feed?limit=${limit}&offset=${offset}`, { headers: this.getHeaders() })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}