import {Injectable} from '@angular/core';
import {RequestMethod, Http, Request} from '@angular/http';
import {Observable} from 'rxjs';
@Injectable()
export class Backend {

  private url: string = BACKEND_URL;

  constructor(private http: Http) {

  }

  request<T>(method: RequestMethod, url: string, body?: {}): Observable<T> {
    return this.http.request(new Request({
        method: method,
        url: this.url + url,
        body: body
      }
    )).map(response => <T> response.json());
  }
}
