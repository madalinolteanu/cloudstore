import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { Account } from './account.model';
import { catchError } from 'rxjs/internal/operators';
import { LocalStorageService } from 'ngx-webstorage';
import { SessionStorageService } from 'ngx-webstorage';
import { CloudStore } from '../cloudstore/cloudstore.model';
import { User } from '../../core/user/user.model';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AccountService {
    private resourceUrl = '/api/cloudstore/account';
    private dashboardUrl = '/dashboard';
    private loginUrl = '/login';

    constructor(
        private http: HttpClient,
        private router: Router,
        private $localStorage: LocalStorageService,
        private $sessionStorage: SessionStorageService
    ) {}

    login(username: string, password: string) {
        const account = new Account();
        account.username = username;
        account.password = password;
        return this.http.post(this.resourceUrl + '/login', account);
    }

    logout() {
        const token = this.$localStorage.retrieve('token');
        this.http.post(this.resourceUrl + '/logout', token).subscribe((data: CloudStore) => {
            console.log('logged out');
        });
        this.$localStorage.clear();
        this.router.navigate([this.loginUrl]);
    }

    register(account): any {
        return this.http.post(this.resourceUrl + '/register', account);
    }

    create(account: Account): Observable<HttpResponse<Account>> {
        return this.http.post<Account>(this.resourceUrl, account, { observe: 'response' });
    }

    update(account: Account): any {
        account.avatar = null;
        const token = this.$localStorage.retrieve('token');
        return this.http.post(this.resourceUrl + '/update?token=' + token, account);
    }

    find(): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.get(`${this.resourceUrl}/get-account?token=`+ token, { observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<Account[]>> {
        const options = createRequestOption(req);
        return this.http.get<Account[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(login: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    authorities(): Observable<string[]> {
        return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
    }

    isAuthenticated(): boolean {
        return this.$localStorage.retrieve('isLogged');
    }

    uploadAvatar(avatar: any): any {
        return this.http.post(this.resourceUrl + '/register/upload-avatar', avatar);
    }
}
