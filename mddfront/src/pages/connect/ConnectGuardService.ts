import { Injectable } from '@angular/core';
import { AuthenticationService } from '../../features/authentication/api/AuthenticationService';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ConnectGuardService {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> {
    return this.authenticationService.isAuthenticated().pipe(
      switchMap((isAuth) => {
        if (isAuth) {
          this.router.navigate(['/posts']);
          return of(true);
        } else {
          return this.authenticationService.tryRefresh().pipe(
            map((refreshSuccess) => {
              if (refreshSuccess) {
                this.router.navigate(['/posts']);
                return true;
              } else {
                return true;
              }
            })
          );
        }
      })
    );
  }
}
