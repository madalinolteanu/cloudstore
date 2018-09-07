import { Injectable } from '@angular/core';
import { Language } from '../language/language.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

@Injectable({
    providedIn: 'root'
})
export class SettingsService {
    private resourceUrl = '/api/cloudstore/settings/languages';

    constructor(private http: HttpClient) {}

    getAllLanguages() {
        debugger;
        return this.http.get(this.resourceUrl).subscribe(data => {
            return data;
        });
    }
}
