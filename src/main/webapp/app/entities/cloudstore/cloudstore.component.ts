import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Directory } from '../directory/directory.model';
import { CloudStoreService } from './cloudstore.service';
import { LocalStorageService } from 'ngx-webstorage';
import { UserFile } from '../file/file.model';
import { CloudStore } from './cloudstore.model';
import { TableData } from '../../shared/models/table-data';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DirectoryComponent} from '../directory/directory.component';
import {ShareComponent} from '../../shared/modals/share/share-modal';
import {MoveComponent} from '../../shared/modals/move/move-modal';
import {DeleteComponent} from '../../shared/modals/delete/delete-modal';
import {SettingsComponent} from '../settings/settings.component';
import {CloudFileComponent} from "../file/file.component";
import {Account} from "../account/account.model";
import {AccountService} from "../account/account.service";
import {LOCAL_PATH, SERVER_PATH} from "../../shared/constants/pagination.constants";

@Component({
    selector: 'jhi-cloudstore',
    templateUrl: './cloudstore.component.html',
    styles: []
})
export class CloudStoreComponent implements OnInit {
    public tableData: TableData[];
    Account: Account;
    folderType: string;
    fileToUpload: File;
    currentDirId: number;
    crumbData: any;
    currentPath: any;
    canMoveButton: boolean;
    canMoveMessage: boolean;
    whereToMoveMessage: boolean;
    filesToMove: any;
    imageUploaded: boolean;
    imageURL: string;
    downloadFile: string;

    constructor(
        private router: Router,
        private $localStorage: LocalStorageService,
        private cloudStoreService: CloudStoreService,
        private nbModal: NgbModal,
        private accountService: AccountService
    ) {}

    ngOnInit() {
        this.canMoveButton = false;
        this.canMoveMessage = false;
        this.whereToMoveMessage = false;
        this.imageUploaded = false;
        this.filesToMove = [];
        this.imageURL = "";
        this.currentDirId = this.$localStorage.retrieve('currentDirId');
        this.downloadFile = '';
        if(this.$localStorage.retrieve('crumbData')){
            this.crumbData = this.$localStorage.retrieve('crumbData');
        } else {
            this.crumbData = [{
                id: -1,
                name: 'Cloud Store'
            }]
        }
        this.Account = new Account();
        if (!this.$localStorage.retrieve('isLogged')) {
            if (this.$localStorage.retrieve('token') == '') {
                this.router.navigate(['/login']);
            }
        }
        this.getUserData();
        this.populateTable(this.currentDirId);
    }

    openFolderModal() {
        this.nbModal.open(DirectoryComponent);
    }

    handleFileInput(files: FileList) {
        if (files != null) this.fileToUpload = files.item(0);
        this.uploadFileToActivity();
    }

    uploadFileToActivity() {
        this.cloudStoreService.upload(this.fileToUpload).subscribe((data: any) => {
            location.reload();
        })
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
        this.canMoveMessage = true;
        this.filesToMove = [id.split('+')[1]];
        this.$localStorage.store('filesToMove', this.filesToMove);
    }

    delete(id: string) {
        this.$localStorage.store('selectedFile', id);
        this.nbModal.open(DeleteComponent);
    }

    download(id: any) {
        let downloadFile = this.downloadFile;
        if(id.split("+")[0] == 'folder') {
            this.cloudStoreService.getFolderAsZip(id.split("+")[1]).subscribe((data: CloudStore) => {
                if(data.successMessage){
                    downloadFile = data.successMessage;
                    document.getElementById("downloadFile").setAttribute('href',downloadFile);
                    document.getElementById("downloadFile").click();
                }
            })
        } else {
            let url = document.getElementById('download+'+id).getAttribute("href");
            url = '/uploads/' + this.Account.userCode + url;
            document.getElementById('download+'+id).setAttribute("href", url);
            document.getElementById('download+'+id).click();
        }
    }

    openFolder(elem: any){
        let id = elem.id;
        let name = elem.fileName;
        const array = id.split('+');
        if(array[0] == 'folder'){
            this.crumbData.push({
                id: array[1],
                name: name
            });
            this.clearBreadCrumb(array[1]);
            this.currentDirId = array[1];
            this.$localStorage.store('currentDirId', this.currentDirId);
            this.$localStorage.store('crumbData', this.crumbData);
            this.populateTable(array[1]);
            if(this.$localStorage.retrieve('filesToMove') && this.$localStorage.retrieve('filesToMove').length > 0){
                let files = this.$localStorage.retrieve('filesToMove');
                if(!(files.indexOf(array[1]) > -1) &&
                    this.tableData.filter(elem => elem.id.split('+')[1] = files[0]).length > 0){
                    this.canMoveMessage = false;
                    this.whereToMoveMessage = true;
                } else {
                    this.canMoveMessage = true;
                    this.whereToMoveMessage = false;
                }
            }
        }
    }

    populateTable(parentId: any) {
        let tableData = this.tableData;
        let folderType = 'folder';
        this.cloudStoreService.getFolders(parentId).subscribe((data: CloudStore) => {
            tableData = Array<TableData>();
            if (data.successMessage != null) {
                let index = 0;
                for (let folder of data.directories as Directory[]) {
                    tableData.push(new TableData());
                    tableData[index].index = index;
                    tableData[index].id = folderType + '+' + folder.id;
                    tableData[index].fileName = folder.directoryName;
                    tableData[index].fileType = folderType;
                    tableData[index].fileUrl = '/uploads' + folder.directoryUrl + folder.directoryName + ".zip";
                    tableData[index].parentId = folder.directoryParent;
                    tableData[index].creationDate = folder.creationDate.substring(0, 10);
                    index++;
                }
                this.cloudStoreService.getFiles(parentId).subscribe((data: CloudStore) => {
                    if (data.successMessage != null) {
                        for (let file of data.files as UserFile[]) {
                            tableData.push(new TableData());
                            tableData[index].index = index;
                            tableData[index].id = file.fileType + '+' + file.id;
                            tableData[index].fileName = file.fileName;
                            tableData[index].fileUrl = file.fileUrl + file.fileName;
                            tableData[index].fileType = file.fileType;
                            tableData[index].parentId = file.directoryId;
                            tableData[index].creationDate = file.creationDate.substring(0, 10);
                            tableData[index].data = file.data;
                            tableData[index].fileUrl = tableData[index].fileUrl.replace(LOCAL_PATH, "");
                            index++;
                        }
                    }
                    this.tableData = tableData;
                });
            }
        });
    }

    clearBreadCrumb(id: any) {
        let canDelete = false;
        let length = this.crumbData.length;
        this.currentPath = '/';
        for(let i = 0; i < this.crumbData.length; i++){
            if(canDelete){
                delete this.crumbData[i];
                length--;
            }
            if(this.crumbData[i] != undefined){
                if(this.crumbData[i].id == id || canDelete){
                    canDelete = true;
                }
            }
        }
        this.crumbData.length = length;
        if(length == 1)
            this.currentPath = "/";
        else {
            for(let i = 0; i < this.crumbData.length; i++) {
                if(i > 0)
                    this.currentPath = this.currentPath + this.crumbData[i].name + "/";
            }
        }
        this.$localStorage.store("currentUrl", this.currentPath);
    }

    getUserData() {
        const router = this.router;
        this.accountService.find().subscribe((data: any) =>{
            if(data != null){
                if(data.status == 500){
                    router.navigate(['/login']);
                } else {
                    this.Account = data.body;
                    if(this.Account.avatar != null){
                        this.imageUploaded = true;
                        this.imageURL = 'data:image/png;base64,' + this.Account.avatar.bytes;
                    }
                    if(this.Account.settings != null){
                        document.body.setAttribute("style", "font-family: " + this.Account.settings.fontType);
                        require("style-loader!../../../content/css/" + this.Account.settings.theme.toLocaleLowerCase() + ".css");
                    }
                }
            }
        });
    }

    cancelMove() {
        this.whereToMoveMessage = false;
        this.$localStorage.store('filesToMove', []);
        this.canMoveMessage = false;
    }

    moveHere() {
        this.cloudStoreService.moveFiles(this.filesToMove, this.currentDirId).subscribe((data: CloudStore) => {
            if(data.successCode == 200){
                console.log("filesMoved");
                this.$localStorage.store('filesToMove', []);
                location.reload();
            }
        });
    }
}
