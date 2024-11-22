import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ErrorHandler {
  handleError(error: any): Observable<never> {
    let errorMessage = 'An unknown error occurred';
    if (error.error && error.error.errors) {
      errorMessage = error.error.errors.join('\n');
    }
    if (error.error && error.error.error) {
      errorMessage = error.error.message;
    }
    return throwError(() => new Error(errorMessage));
  }
}
