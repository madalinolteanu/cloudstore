import { Component, OnInit } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { AccountService } from '../account/account.service';
import { Router } from '@angular/router';
import {Account} from "../account/account.model";
import {CloudStore} from "../cloudstore/cloudstore.model";

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styles: []
})
export class RegisterComponent implements OnInit {
    account: Account;
    registerAccount: any;
    isVisible: boolean;
    loginUrl: '/login';
    displayGlyph: boolean;
    imageURL: string;
    imageUploaded: boolean;
    imageToUpload;

    constructor(private languageService: JhiLanguageService, private accountService: AccountService, private router: Router) {}

    ngOnInit() {
        this.imageURL = "";
        this.imageUploaded = false;
        this.isVisible = true;
        this.displayGlyph = true;
        this.registerAccount = {};
    }

    register() {
        const account = new Account();
        account.username = this.registerAccount.username;
        account.password = this.registerAccount.password;
        account.email = this.registerAccount.email;
        account.firstName = this.registerAccount.firstName;
        account.lastName = this.registerAccount.lastName;
        account.imageUrl = 'test';
        account.userType = 'BASIC';
        debugger;
        this.accountService.register(account).subscribe((data: CloudStore) => {
            if(data.successCode == 200 ){
                this.isVisible = false;
            }
        });
    }

    navigateLogin() {
        this.router.navigate(['/login']);
    }

    handleFileInput(event: any) {
        const files = event.target.files;
        const reader = new FileReader();

        reader.readAsDataURL(event.target.files[0]); // read file as data url

        reader.onload = (event: any) => { // called once readAsDataURL is completed
            this.imageURL = event.target.result;
            this.imageUploaded = true;
        };
        if (files != null) this.imageToUpload = files.item(0);
        // this.uploadFileToActivity();
    }
}
