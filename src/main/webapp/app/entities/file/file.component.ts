import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
@Component({
    selector: 'jhi-file',
    templateUrl: './file.component.html',
    styles: []
})

export class CloudFileComponent implements OnInit {
    @Input() public genericObj: any;

    constructor(
        public activeModal: NgbActiveModal,
        private addFolderModal: NgbModal,
        private cloudService: CloudStoreService,
        private router: Router,
    ) {}

    ngOnInit() {
        console.log(this.genericObj);
    }

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }
}
