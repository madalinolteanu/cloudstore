import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { AccountService } from '../account/account.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styles: []
})
export class RegisterComponent implements OnInit {
    account: Account;
    registerAccount: any;
    loginUrl: '/login';

    constructor(private languageService: JhiLanguageService, private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        this.registerAccount = {};
    }

    register() {
        this.accountService.register(this.registerAccount);
    }

    navigateLogin() {
        this.router.navigate(['/login']);
    }
}
