import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {CloudStore} from "../cloudstore/cloudstore.model";
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-directory',
    templateUrl: './directory.component.html',
    styles: []
})
export class DirectoryComponent implements OnInit {
    newFolderName: string;
    canCreateDir: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private addFolderModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router
    ) {}

    ngOnInit() {
        this.canCreateDir = false;
    }

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }

    addFolder() {
        this.cloudService.addFolder(this.newFolderName).subscribe((data: CloudStore) =>{
            if(data != null && data.successMessage != null) {
                this.activeModal.dismiss('success');
                location.reload();
            } else {
                this.canCreateDir = true;
            }
        });
    }
}
