import {AfterViewChecked, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {SettingsService} from "./settings.service";
import {Router} from "@angular/router";
import {AccountService} from "../account/account.service";
import {Account} from "../account/account.model";
import {CloudStore} from "../cloudstore/cloudstore.model";
import {Language} from "../language/language.model";
import {Font} from "../font/font.model";
import {Theme} from "../theme/theme.model";
import {Settings} from "./settings.model";

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
    settings: Settings;
    isVisible: boolean;
    loginUrl: '/login';
    displayGlyph: boolean;
    imageURL: string;
    imageUploaded: boolean;
    imageToUpload: string;
    oldPassword: string;
    imageChanged: boolean;

    errorEmailExists: boolean;
    oldNotMath: boolean;

    languages: Language[];
    fonts: Font[];
    themes: Theme[];

    @ViewChild('jhi-settings') textarea: ElementRef;

    constructor(
        public activeModal: NgbActiveModal,
        private settingsModal: NgbModal,
        private cloudService: CloudStoreService,
        private accountService: AccountService,
        private router: Router,
        private settingsService: SettingsService,
        private el: ElementRef
    ) {}

    ngOnInit() {
        this.isGeneral = true;
        this.cssActiveG = 'active';
        this.cssActiveP = '';
        this.cssActiveS = '';
        this.isSecurity = false;
        this.isPersonal = false;

        this.errorEmailExists = false;
        this.oldNotMath = false;

        this.imageURL = '';
        this.imageUploaded = false;
        this.isVisible = true;
        this.displayGlyph = true;
        this.settings = new Settings();
        this.oldPassword = '';
        this.imageChanged = false;
        this.account = new Account();
        this.getLanguages();
        this.getFonts();
        this.getThemes();
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
        this.accountService.find().subscribe((data: Account) =>{
            if(data != null){
                this.account = data.body;
                if(this.account.avatar != null){
                    this.imageUploaded = true;
                    this.imageURL = 'data:image/png;base64,' + this.account.avatar.bytes;
                }
                this.settings = data.body.settings;
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
            this.imageChanged = true;
        };
        if (files != null) {
            this.account.avatar = files.item(0);
        }
    }

    saveSettings() {
        const $accountService = this.accountService;
        const account = this.account;
        const avatar = this.account.avatar;
        if(this.imageChanged){
            account.imageUrl = null;
        }
        this.accountService.update(this.account).subscribe((data: CloudStore) =>{
            if(data.successCode == 200){
                if(this.imageChanged) {
                    const formData: FormData = new FormData();
                    formData.append('fileKey', avatar, account.userCode);
                    formData.set("name", account.userCode, avatar.name);
                    $accountService.uploadAvatar(formData).subscribe((upload: CloudStore) => {
                        if (upload.successCode == 200) {
                            location.reload();
                        }
                    });
                } else {
                    location.reload();
                }
            } else if (data.errorCode == 500) {

            } else if (data.errorCode == 501) {
                this.errorEmailExists = true;
            } else if (data.errorCode == 502) {
                this.oldNotMath = true;
            }
        })
    }

    getLanguages() {
        this.settingsService.getAllLanguages().subscribe((data: Language[]) => {
            if(data != null){
                this.languages = data;
            }
        });
    }

    getFonts() {
        this.settingsService.getAllFonts().subscribe((data: Font[]) => {
            if(data != null){
                this.fonts = data;
            }
        });
    }

    getThemes() {
        this.settingsService.getAllThemes().subscribe((data: Theme[]) => {
            if(data != null){
                this.themes = data;
            }
        });
    }

    changeLanguage(){
        this.settings.language = this.languages[event.target.selectedIndex].languageCode;
    }

    changeTheme(){
        this.settings.theme = this.themes[event.target.selectedIndex].themeCode;
    }

    changeFont(){
        this.settings.fontType = this.fonts[event.target.selectedIndex].fontCode;
    }
}
