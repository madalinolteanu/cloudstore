import { Injectable } from '@angular/core';
import { CloudStore } from './cloudstore.model';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserFile } from '../file/file.model';
import {Directory} from "../directory/directory.model";

@Injectable({
    providedIn: 'root'
})
export class CloudStoreService {
    private resourceUrl = '/api/cloudstore';
    private folderType = 'folder';

    constructor(private http: HttpClient, private router: Router, private $localStorage: LocalStorageService) {}

    getFolders(parentId: any): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.get(this.resourceUrl + '/directories' + '?token=' + token + '&directoryId=' + parentId);
    }

    getFiles(parentId): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.get(this.resourceUrl + '/files' + '?token=' + token + '&directoryId=' + parentId);
    }

    upload(fileToUpload: File) {
        const userFile = this.uploadFile(fileToUpload);
        const token = this.$localStorage.retrieve('token');
        const formData: FormData = new FormData();
        formData.append('fileKey', fileToUpload, fileToUpload.name);
        const url = this.$localStorage.retrieve("currentUrl");
        return this.http.post(this.resourceUrl + '/upload?token=' + token + '&url=' + url, formData)
    }

    uploadFile(fileToUpload: File): any {
        const formData: FormData = new FormData();
        formData.append('fileKey', fileToUpload, fileToUpload.name);
        const token = this.$localStorage.retrieve('token');
        const userFile = new UserFile();
        userFile.fileName = fileToUpload.name;
        userFile.fileType = fileToUpload.type;
        userFile.fileUrl = this.$localStorage.retrieve('currentUrl');
        userFile.directoryId = this.$localStorage.retrieve('currentDirId');
        return this.http.post(this.resourceUrl + '/file/upload?token=' + token, userFile).subscribe((data: CloudStore) => {
            if (data.successMessage != null) {
                return data.files[0];
            } else return null;
        });
    }

    addFolder(addFolder: string): any {
        const newFolder = new Directory();
        newFolder.directoryName = addFolder;
        newFolder.directoryParent = this.$localStorage.retrieve('currentDirId');
        newFolder.directoryUrl = this.$localStorage.retrieve('currentUrl');
        const token = this.$localStorage.retrieve('token');
        return this.http.post(this.resourceUrl + '/directory/create?token=' + token, newFolder);
    }

    deleteFile(): any {
        const selectedFile = this.$localStorage.retrieve('selectedFile');
        const array = selectedFile.split("+");
        const token = this.$localStorage.retrieve('token');
        if(array[0] == this.folderType){
            return this.http.post(this.resourceUrl + '/directory/delete?token=' + token, array[1]);
        } else {
            return this.http.post(this.resourceUrl + '/file/delete?token=' + token, array[1]);
        }
    }

    moveFiles(filesToMove: any, parentId: any): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.post(this.resourceUrl + '/directory/move?token=' + token +
            '&directoryId=' + parentId, filesToMove);
    }
}
