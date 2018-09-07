import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Directory } from '../directory/directory.model';
import { CloudStoreService } from './cloudstore.service';
import { LocalStorageService } from 'ngx-webstorage';
import { UserFile } from '../file/file.model';
import { CloudStore } from './cloudstore.model';
import { TableData } from '../../shared/models/table-data';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DirectoryComponent} from "../directory/directory.component";
import {ShareComponent} from "../../shared/modals/share/share-modal";
import {MoveComponent} from "../../shared/modals/move/move-modal";
import {DeleteComponent} from "../../shared/modals/delete/delete-modal";
import {SettingsComponent} from "../settings/settings.component";

@Component({
    selector: 'jhi-cloudstore',
    templateUrl: './cloudstore.component.html',
    styles: []
})
export class CloudStoreComponent implements OnInit {
    public tableData: TableData[];
    folderType: string;
    fileToUpload: File;
    currentDirId: number;

    constructor(
        private router: Router,
        private $localStorage: LocalStorageService,
        private cloudStoreService: CloudStoreService,
        private nbModal: NgbModal
    ) {}

    ngOnInit() {
        this.currentDirId = -1;
        this.$localStorage.store('currentDirId', this.currentDirId);
        if (!this.$localStorage.retrieve('isLogged')) {
            if (this.$localStorage.retrieve('token') == '') {
                this.router.navigate(['/login']);
            }
        }
        let tableData = this.tableData;
        let folderType = 'folder';
        this.cloudStoreService.getFolders().subscribe((data: CloudStore) => {
            tableData = Array<TableData>();
            if (data.successMessage != null) {
                let index = 0;
                for (let folder of data.directories as Directory[]) {
                    tableData.push(new TableData());
                    tableData[index].index = index;
                    tableData[index].id = folderType + "-" + folder.id;
                    tableData[index].fileName = folder.directoryName;
                    tableData[index].fileType = folderType;
                    tableData[index].fileUrl = folder.directoryUrl;
                    tableData[index].parentId = folder.directoryParent;
                    tableData[index].creationDate = folder.creationDate.substring(0, 10);
                    index++;
                }
                this.cloudStoreService.getFiles().subscribe((data: CloudStore) => {
                    if (data.successMessage != null) {
                        for (let file of data.files as UserFile[]) {
                            tableData.push(new TableData());
                            tableData[index].index = index;
                            tableData[index].id = file.fileType + "-" + file.id;
                            tableData[index].fileName = file.fileName;
                            tableData[index].fileUrl = file.fileType;
                            tableData[index].fileName = file.fileName;
                            tableData[index].parentId = file.directoryId;
                            tableData[index].creationDate = file.creationDate.substring(0, 10);
                            index++;
                        }
                    }
                    this.tableData = tableData;
                });
            }
        });
    }

    openFolderModal() {
        this.nbModal.open(DirectoryComponent);
    }

    handleFileInput(files: FileList) {
        if (files != null) this.fileToUpload = files.item(0);
        this.uploadFileToActivity();
    }

    uploadFileToActivity() {
        this.cloudStoreService.upload(this.fileToUpload);
    }

    openModal() {
        console.log('modalOpened');
    }

    openSettings() {
        this.nbModal.open(SettingsComponent);
    }

    share(id: string) {
        this.nbModal.open(ShareComponent);
    }

    move(id: string) {
        this.nbModal.open(MoveComponent);
    }

    delete(id: string) {
        this.$localStorage.store('selectedFile', id);
        this.nbModal.open(DeleteComponent);

    }
}
