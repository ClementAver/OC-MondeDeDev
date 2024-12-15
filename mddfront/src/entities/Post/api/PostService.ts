import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../shared/config/environment';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Post } from '../model/Post.interface';

@Injectable({ providedIn: 'root' })
export class PostService {
  private apiURL = environment.apiURL;

  constructor(
    private httpClient: HttpClient,
    private errorHandler: ErrorHandler,
    private authenticationService: AuthenticationService
  ) {}

  createPost(
    title: string,
    content: string,
    topic: number,
    user: number
  ): Observable<Post> {
    return this.httpClient
      .post<Post>(
        `${this.apiURL}/post`,
        { title, content, topic, user },
        {
          headers: this.authenticationService.getHeaders(),
        }
      )
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  getPost(postId: number): Observable<Post> {
    return this.httpClient
      .get<Post>(`${this.apiURL}/post/${postId}`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}
