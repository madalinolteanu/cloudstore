import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VERSION } from 'app/app.constants';
import { AccountService } from '../../entities/account/account.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.css']
})
export class NavbarComponent implements OnInit {
    isNavbarCollapsed: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(private accountService: AccountService) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {}

    isAuthenticated(): boolean {
        return this.accountService.isAuthenticated();
    }

    logout() {
        return this.accountService.logout();
    }
}
