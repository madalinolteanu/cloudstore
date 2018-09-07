import { Component, OnDestroy, OnInit } from '@angular/core';
import { Account } from '../account/account.model';
import { AccountService } from '../account/account.service';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService } from '../../core/login/login.service';
import { SettingsService } from '../settings/settings.service';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-login',
    templateUrl: './login.component.html',
    styles: []
})
export class LoginComponent implements OnInit, OnDestroy {
    account: Account;
    username: string;
    password: string;
    dashboardUrl: '/dashboard';
    registerUrl: '/register';

    constructor(
        private accountService: AccountService,
        private systemService: LoginService,
        private eventManager: JhiEventManager,
        private settingsSergice: SettingsService,
        private $localStorage: LocalStorageService,
        private router: Router
    ) {}

    ngOnInit() {
        this.username = '';
        this.password = '';
        this.systemLogin();
        if (this.$localStorage.retrieve('isLogged')) {
            this.router.navigate(['/dashboard']);
        }
    }

    ngOnDestroy() {}

    login() {
        this.accountService.login(this.username, this.password);
    }

    systemLogin() {
        this.systemService
            .login({
                username: 'user',
                password: 'user',
                rememberMe: true
            })
            .then(() => {
                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });
            })
            .catch(() => {
                console.log('authenticationError');
            });
    }

    requestResetPassword() {
        // this.accountService.forgotPassword(this.username, this.password);
    }

    navigateRegister() {
        this.router.navigate(['/register']);
    }
}
