import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CloudStoreService} from '../../../entities/cloudstore/cloudstore.service';
import {CloudStore} from '../../../entities/cloudstore/cloudstore.model';
import {Router} from '@angular/router';
import {LocalStorageService} from 'ngx-webstorage';

@Component({
    selector: 'jhi-delete-modal',
    templateUrl: './delete-modal.html',
    styles: []
})
export class DeleteComponent implements OnInit {
    constructor(
        public activeModal: NgbActiveModal,
        private deleteModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router,
        private $localStorage: LocalStorageService
    ) {}

    ngOnInit() {}

    modalDismiss(){
        this.activeModal.dismiss('cancel');
        this.$localStorage.store('selectedFile', null);
    }

    deleteFile() {
        this.cloudService.deleteFile().subscribe((data: CloudStore) =>{
            if(data != null && data.successMessage != null) {
                debugger;
                this.activeModal.dismiss('success');
                this.$localStorage.store('selectedFile', null);
                location.reload();
            } else {
                console.log(data.errorMessage);
            }
        });
    }
}
