import { Injectable } from '@angular/core';
import { environment } from '../../../shared/config/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { AuthenticationService } from '../../../features/authentication/api/AuthenticationService';
import { catchError, Observable, throwError } from 'rxjs';
import { User as MeResponse } from '../../../entities/User/model/User.interface';

@Injectable({ providedIn: 'root' })
export class ProfileService {
  private apiURL = environment.apiURL;

  constructor(
    private httpClient: HttpClient,
    private errorHandler: ErrorHandler,
    private authenticationService: AuthenticationService
  ) {}

  updateUser(
    id: number,
    requestBody: {
      name: string;
      email: string;
    }
  ): Observable<MeResponse> {
    const accessToken = this.authenticationService.getAccessToken();
    if (!accessToken) {
      return throwError(() => new Error('Access token is not set'));
    }
    const headers = new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });

    return this.httpClient
      .put<MeResponse>(`${this.apiURL}/user/${id}`, requestBody, { headers })
      .pipe(catchError((error) => this.errorHandler.handleError(error)));
  }
}
