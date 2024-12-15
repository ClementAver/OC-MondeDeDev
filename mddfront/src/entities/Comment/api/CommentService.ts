import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../shared/config/environment';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Comment } from '../model/Comment.interface';

@Injectable({ providedIn: 'root' })
export class CommentService {
  private apiURL = environment.apiURL;

  constructor(
    private httpClient: HttpClient,
    private errorHandler: ErrorHandler,
    private authenticationService: AuthenticationService
  ) {}



  getCommentsByPostId(postId: number): Observable<Comment[]> {
    return this.httpClient
      .get<Comment[]>(`${this.apiURL}/comment/post/${postId}`, {
        headers: this.authenticationService.getHeaders(),
      })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }

  createComment(
    content: string,
    post: number,
    user: number
  ): Observable<Comment> {
    return this.httpClient
      .post<Comment>(
        `${this.apiURL}/comment`,
        { content, post, user },
        {
          headers: this.authenticationService.getHeaders(),
        }
      )
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}
