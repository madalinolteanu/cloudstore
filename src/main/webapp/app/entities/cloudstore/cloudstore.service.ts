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

    constructor(private http: HttpClient, private router: Router, private $localStorage: LocalStorageService) {}

    getFolders(): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.get(this.resourceUrl + '/directories' + '?token=' + token + '&directoryId=-1');
    }

    getFiles(): any {
        const token = this.$localStorage.retrieve('token');
        return this.http.get(this.resourceUrl + '/files' + '?token=' + token + '&directoryId=-1');
    }

    upload(fileToUpload: File) {
        const userFile = this.uploadFile(fileToUpload);
        // const token = this.$localStorage.retrieve('token');
        // const formData: FormData = new FormData();
        // formData.append('fileKey', fileToUpload, fileToUpload.name);
        // this.http
        //     .post(this.resourceUrl + '/upload?token=' + token + '&directoryId=' + userFile.directoryId + '&fileId=' + userFile.id, formData)
        //     .subscribe((data: CloudStore) => {
        //         if (data.successMessage != null) {
        //             return data.successMessage;
        //         } else return null;
        //     });
    }

    uploadFile(fileToUpload: File): any {
        const formData: FormData = new FormData();
        formData.append('fileKey', fileToUpload, fileToUpload.name);
        const token = this.$localStorage.retrieve('token');
        const userFile = new UserFile();
        userFile.fileName = fileToUpload.name;
        userFile.fileType = fileToUpload.type;
        userFile.fileUrl = '/';
        return this.http.post(this.resourceUrl + '/file/upload?token=' + token, userFile).subscribe((data: CloudStore) => {
            if (data.successMessage != null) {
                return data.files[0];
            } else return null;
        });
    }

    addFolder(addFolder: string): any {
        const newFolder = new Directory();
        newFolder.directoryName = addFolder;
        newFolder.directoryUrl = ".";
        newFolder.directoryParent = -1;
        const token = this.$localStorage.retrieve('token');
        return this.http.post(this.resourceUrl + '/directory/create?token=' + token, newFolder);
    }
}
