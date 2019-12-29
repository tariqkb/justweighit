import { Injectable } from '@angular/core'
import { Observable } from 'rxjs'
import { environment } from '../../../environments/environment'
import { HttpClient } from '@angular/common/http'
import { FoodResponse, SearchResponse } from '../backend.types'

@Injectable()
export class Backend {
  private url: string = environment.apiUrl

  constructor(private http: HttpClient) {}

  getFood(ndbno: string, amount?: string, unit?: string): Observable<FoodResponse> {
    return this.http.get<FoodResponse>(`${this.url}/food/${ndbno}`, {
      params: sanitizeQueryParams({ amount, unit }),
    })
  }

  search(text: string): Observable<SearchResponse> {
    return this.http.post<SearchResponse>(`${this.url}/search`, { text })
  }
}

function sanitizeQueryParams(params: { [key: string]: any }) {
  return Object.entries(params).reduce((p, [key, val]) => {
    console.log(key, val)
    if (val !== null && val !== undefined) {
      p[key] = val
    }
    return p
  }, {})
}
