import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/do';
import { OktaAuthService } from './okta.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    
    constructor(private oktaService: OktaAuthService){}
    
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.oktaService.isAuthenticated()) {
            const accessToken = this.oktaService.signIn.tokenManager.get('accessToken');
            request = request.clone({
              setHeaders: {
                Authorization: `${accessToken.tokenType} ${accessToken.accessToken}`
              }
            });
          }

          return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
              return event;
            } else if (event instanceof HttpErrorResponse) {
              if (event.status === 401) {
                this.oktaService.login();
              }
            }
          });
    }
}