/**
 * Created by Madalin on 9/7/2018.
 */
import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CloudStoreService} from "../../../entities/cloudstore/cloudstore.service";
import {CloudStore} from "../../../entities/cloudstore/cloudstore.model";
import {Router} from "@angular/router";
import {LocalStorageService} from "ngx-webstorage";

@Component({
    selector: 'jhi-move-modal',
    templateUrl: './move-modal.html',
    styles: []
})
export class MoveComponent implements OnInit {
    constructor(
        public activeModal: NgbActiveModal,
        private moveModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {}

    modalDismiss(){
        this.activeModal.dismiss('cancel');
        this.$localStorage.store('selectedFile', null);
    }

    moveFile() {
        // this.cloudService.addFolder(this.newFolderName).subscribe((data: CloudStore) =>{
        //     if(data != null && data.successMessage != null) {
        //         debugger;
        //         this.activeModal.dismiss('success');
        //         location.reload();
        //     } else {
        //         console.log(data.successMessage);
        //     }
        // });
    }
}
