import { Injectable } from '@angular/core';
import { environment } from '../../../shared/config/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import type { LoginResponse } from '../model/LoginResponse.interface';
import { User as MeResponse } from '../../../entities/User/model/User.interface';
import { tap } from 'rxjs/operators';
import { catchError, Observable, throwError, of } from 'rxjs';
import { Router } from '@angular/router';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private apiURL = environment.apiURL;
  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private errorHandler: ErrorHandler
  ) {}

  getAccessToken() {
    return sessionStorage.getItem('token') || '';
  }

  getRefreshToken() {
    return localStorage.getItem('refreshToken') || '';
  }

  login(requestBody: {
    name: string;
    email: string;
    password: string;
  }): Observable<LoginResponse> {
    return this.httpClient
      .post<LoginResponse>(`${this.apiURL}/auth/login`, requestBody)
      .pipe(
        tap((response) => {
          sessionStorage.setItem('token', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
        }),
        catchError(this.errorHandler.handleError)
      );
  }

  logout() {
    sessionStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    this.router.navigate(['/connect']);
  }

  register(requestBody: {
    name: string;
    email: string;
    password: string;
  }): Observable<LoginResponse> {
    return this.httpClient
      .post<LoginResponse>(`${this.apiURL}/auth/register`, requestBody)
      .pipe(
        tap((response) => {
          sessionStorage.setItem('token', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
          this.router.navigate(['/posts']);
        }),
        catchError(this.errorHandler.handleError)
      );
  }

  me(): Observable<MeResponse> {
    const accessToken = this.getAccessToken();
    if (!accessToken) {
      throwError(() => new Error('Access token is not set'));
      if (!this.tryRefresh()) this.logout();
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });

    return this.httpClient
      .get<MeResponse>(`${this.apiURL}/auth/me`, {
        headers,
      })
      .pipe(catchError(this.errorHandler.handleError));
  }

  refresh(): Observable<LoginResponse> {
    const refreshToken = this.getRefreshToken();

    if (!refreshToken) {
      return throwError(() => new Error('Refresh token is not set'));
    }

    const headers = new HttpHeaders({
      'Content-Type': 'text/plain',
    });

    return this.httpClient
      .post<LoginResponse>(
        `${this.apiURL}/auth/refresh`,
        this.getRefreshToken()
      )
      .pipe(
        tap((response) => {
          sessionStorage.setItem('token', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
        }),
        catchError(this.errorHandler.handleError)
      );
  }

  isAuthenticated(): Observable<boolean> {
    return this.me().pipe(
      map((result) => {
        return !!result.id;
      }),
      catchError(() => of(false))
    );
  }

  tryRefresh(): Observable<boolean> {
    return this.refresh().pipe(
      map((result) => !!result.token),
      catchError(() => of(false))
    );
  }
}
