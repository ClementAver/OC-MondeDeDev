import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationService } from '../api/AuthenticationService';
import { Observable, of } from 'rxjs';
import { ErrorHandler } from '../../../shared/utility/ErrorHandler';
import { map, switchMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorHandler: ErrorHandler
  ) {}

  canActivate(): Observable<boolean> {
    return this.authenticationService.isAuthenticated().pipe(
      map((isAuth) => {
        if (!isAuth) {
          this.router.navigate(['/connect']);
          return false;
        }
        return true;
      }),
      switchMap((isAuthenticated) => of(isAuthenticated))
    );
  }
}
