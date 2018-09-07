import { Account } from '../account/account.model';
import { Directory } from '../directory/directory.model';
import { UserFile } from '../file/file.model';

export class CloudStore {
    constructor(
        public errorMessage?: string,
        public successMessage?: string,
        public errorCode?: number,
        public successCode?: number,
        public userDTO?: Account,
        public directories?: Directory[],
        public files?: UserFile[]
    ) {}
}
