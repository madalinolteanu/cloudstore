import { Injectable } from '@angular/core';
import { Language } from '../language/language.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

@Injectable({
    providedIn: 'root'
})
export class SettingsService {
    private resourceUrl = '/api/cloudstore/settings';

    constructor(private http: HttpClient) {}

    getAllLanguages(): any {
        return this.http.get(this.resourceUrl + "/languages");
    }

    getAllFonts(): any {
        return this.http.get(this.resourceUrl + '/fonts');
    }

    getAllThemes(): any {
        return this.http.get(this.resourceUrl + '/themes');
    }
}
