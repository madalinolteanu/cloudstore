import { BaseEntity } from '../../shared/models/base-entity';
export class UserFile implements BaseEntity {
    constructor(
        public id?: number,
        public fileName?: string,
        public fileType?: string,
        public userCode?: string,
        public fileUrl?: string,
        public directoryId?: number,
        public creationDate?: string
    ) {}
}
