import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {Router} from "@angular/router";
import {AccountService} from "../account/account.service";
import {Account} from "../account/account.model";
import {CloudStore} from "../cloudstore/cloudstore.model";

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html',
    styles: []
})
export class SettingsComponent implements OnInit, AfterViewChecked{
    isGeneral: boolean;
    isSecurity: boolean;
    isPersonal: boolean;
    cssActiveG: string;
    cssActiveS: string;
    cssActiveP: string;

    account: Account;
    settings: any;
    isVisible: boolean;
    loginUrl: '/login';
    displayGlyph: boolean;
    imageURL: string;
    imageUploaded: boolean;
    imageToUpload: string;
    oldPassword: string;

    @ViewChild('jhi-settings') textarea: ElementRef;

    constructor(
        public activeModal: NgbActiveModal,
        private settingsModal: NgbModal,
        private cloudService: CloudStoreService,
        private accountService: AccountService,
        private router: Router,
        private el: ElementRef
    ) {}

    ngOnInit() {
        this.isGeneral = true;
        this.cssActiveG = 'active';
        this.cssActiveP = '';
        this.cssActiveS = '';
        this.isSecurity = false;
        this.isPersonal = false;

        this.imageURL = '';
        this.imageUploaded = false;
        this.isVisible = true;
        this.displayGlyph = true;
        this.settings = {};
        this.oldPassword = '';
        this.account = new Account();
        this.populateWindow();
    }

    ngAfterViewChecked() {
        console.log("checked");
        if(document.getElementsByClassName('modal-dialog')[0] != undefined){
            document.getElementsByClassName('modal-dialog')[0].setAttribute("class", "modal-dialog modal-lg");
        }
    }

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }

    changeTab(tab: string) {
       if(tab == 'general'){
           this.isSecurity = false;
           this.isGeneral = true;
           this.isPersonal = false;
           this.cssActiveG = 'active';
           this.cssActiveS = '';
           this.cssActiveP = '';
       } else if (tab == 'personal'){
           this.isSecurity = false;
           this.isGeneral = false;
           this.isPersonal = true;
           this.cssActiveG = '';
           this.cssActiveS = '';
           this.cssActiveP = 'active';
       } else {
           this.isSecurity = true;
           this.isGeneral = false;
           this.isPersonal = false;
           this.cssActiveG = '';
           this.cssActiveS = 'active';
           this.cssActiveP = '';
       }
    }

    populateWindow() {
        this.accountService.find().subscribe((data: any) =>{
            if(data != null){
                this.account = data.body;
                if(this.account.avatar != null){
                    this.imageUploaded = true;
                    this.imageURL = 'data:image/png;base64,' + this.account.avatar.bytes;
                }
            }
        });
    }

    handleFileInput(event: any) {
        const files = event.target.files;
        const reader = new FileReader();

        reader.readAsDataURL(event.target.files[0]); // read file as data url

        reader.onload = (event: any) => {
            this.imageURL = event.target.result;
            this.imageUploaded = true;
        };
        if (files != null) {
            this.account.avatar = files.item(0);
        }
    }

    saveSettings() {
        const $accountService = this.accountService;
        const account = this.account;
        const avatar = this.account.avatar;
        this.accountService.update(this.account).subscribe((data: CloudStore) =>{
            if(data.successCode == 200){
                const formData: FormData = new FormData();
                formData.append('fileKey', avatar, account.userCode);
                formData.set("name", account.userCode, avatar.name);
                $accountService.uploadAvatar(formData).subscribe((upload: CloudStore) => {
                    if(upload.successCode == 200) {
                        location.reload();
                    }
                });
            } else if (data.errorCode == 500) {

            } else if (data.errorCode == 501) {

            } else if (data.errorCode == 502) {

            }
        })
    }
}
