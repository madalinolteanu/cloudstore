/**
 * Created by Madalin on 9/7/2018.
 */
import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CloudStoreService} from "../../../entities/cloudstore/cloudstore.service";
import {CloudStore} from "../../../entities/cloudstore/cloudstore.model";
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-share-modal',
    templateUrl: './share-modal.html',
    styles: []
})
export class ShareComponent implements OnInit {
    constructor(
        public activeModal: NgbActiveModal,
        private addFolderModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router
    ) {}

    ngOnInit() {}

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }

    shareFile() {
        // this.cloudService.addFolder(this.newFolderName).subscribe((data: CloudStore) =>{
        //     if(data != null && data.successMessage != null) {
        //         debugger;
        //         location.reload();
        //     } else {
        //         console.log(data.successMessage);
        //     }
        // });
    }
}
