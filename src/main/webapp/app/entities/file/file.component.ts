import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CloudStoreService} from "../cloudstore/cloudstore.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {LocalStorageService} from "ngx-webstorage";
import {SERVER_PATH} from "../../shared/constants/pagination.constants";
import {DomSanitizer} from "@angular/platform-browser";
import {HttpClient} from "@angular/common/http";
@Component({
    selector: 'jhi-file',
    templateUrl: './file.component.html',
    styles: []
})

export class CloudFileComponent implements OnInit {
    fileUrl: string;
    constructor(
        public activeModal: NgbActiveModal,
        private addFolderModal: NgbModal,
        private cloudService: CloudStoreService,
        private $localStorage: LocalStorageService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private http: HttpClient,
    ) {}

    ngOnInit() {
        this.fileUrl = SERVER_PATH + "/" + this.$localStorage.retrieve("selectedFile");
        this.loadDoc();
    }

    modalDismiss(){
        this.activeModal.dismiss('cancel');
    }

    loadDoc() {
        this.http.get(this.fileUrl).subscribe((data: any) =>{
            console.log(data);
        })
    }
}
