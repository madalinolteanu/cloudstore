import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html',
    styles: []
})
export class SettingsComponent implements OnInit {
    constructor(
        public activeModal: NgbActiveModal,
        private settingsModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router
    ) {}

    ngOnInit() {}

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }

    saveSettings() {

    }
}
