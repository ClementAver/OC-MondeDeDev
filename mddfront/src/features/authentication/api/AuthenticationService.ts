import { Injectable } from '@angular/core';
import { environment } from '../../../shared/config/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import type { LoginResponse } from '../model/LoginResponse.interface';
import { User as MeResponse } from '../../../entities/User/model/User.interface';
import { tap } from 'rxjs/operators';
import { catchError, Observable, throwError, of } from 'rxjs';
import { Router } from '@angular/router';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { map, switchMap } from 'rxjs/operators';

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
        catchError((error) => this.errorHandler.handleError(error))
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
        catchError((error) => this.errorHandler.handleError(error))
      );
  }

  me(skipAlert: boolean = false): Observable<MeResponse> {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      return throwError(() => new Error('Access token absent.'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });

    return this.httpClient
      .get<MeResponse>(`${this.apiURL}/auth/me`, {
        headers,
      })
      .pipe(
        catchError((error) => this.errorHandler.handleError(error, skipAlert))
      );
  }

  refresh(): Observable<LoginResponse> {
    const refreshToken = this.getRefreshToken();

    if (!refreshToken) {
      return throwError(() => new Error('Refresh token absent.'));
    }

    return this.httpClient
      .post<LoginResponse>(`${this.apiURL}/auth/refresh`, refreshToken)
      .pipe(
        tap((response) => {
          sessionStorage.setItem('token', response.token);
          localStorage.setItem('refreshToken', response.refreshToken);
        }),
        catchError((error) => this.errorHandler.handleError(error))
      );
  }

  /*
   * - Access and refresh tokens aren't truthty ('') -> false.
   * - me() ? fasle : refresh().
   * - refresh() ? me : false.
   * - me() ? true : false.
   */
  isAuthenticated(skipAlert: boolean = false): Observable<boolean> {
    const accessToken = this.getAccessToken();
    const refreshToken = this.getRefreshToken();

    if (!accessToken && !refreshToken) {
      this.errorHandler.handleError(
        new Error('Vos accréditations ont expiré, veuillez vous reconnecter.'),
        skipAlert
      );
      return of(false);
    }

    return this.me(skipAlert).pipe(
      map(() => true),
      catchError(() => {
        if (refreshToken) {
          return this.refresh().pipe(
            switchMap(() => this.me(skipAlert)),
            map(() => true),
            catchError((error) => {
              this.errorHandler.handleError(error);
              return of(false);
            })
          );
        }
        return of(false);
      })
    );
  }
}
