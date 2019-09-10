import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { retry, catchError } from "rxjs/operators";

export class HttpErrorInterceptor implements HttpInterceptor {

	intercept( request: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {
		return next.handle( request ).pipe(
			retry( 1 ),
			catchError(( error: HttpErrorResponse ) => {
				const errorMessage = ( error.error instanceof ErrorEvent ) ?
					`Error: ${error.error.message}` : `Error Code: ${error.status}\nError Message: ${error.message}`;
				return throwError( errorMessage );
			} ) );
	}

}