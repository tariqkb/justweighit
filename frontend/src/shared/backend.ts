import {Injectable} from '@angular/core';
import {RequestMethod, Http, Request, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs';
@Injectable()
export class Backend {

  private url: string = BACKEND_URL;

  constructor(private http: Http) {

  }

  request<T>(method: RequestMethod, url: string, body?: {}, search?: {}): Observable<T> {
    let queryParams = null;
    if(search) {
      queryParams = new URLSearchParams();
      Object.keys(search).forEach(key => {
        queryParams.set(key, search[key]);
      });
    }

    return this.http.request(new Request({
        method: method,
        url: this.url + url,
        body: body,
        search: queryParams
      }
    )).map(response => <T> response.json());
  }
}
