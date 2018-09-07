import { BaseEntity } from '../../shared/models/base-entity';
export class Directory implements BaseEntity {
    constructor(
        public id?: number,
        public directoryName?: string,
        public userCode?: string,
        public directoryUrl?: string,
        public directoryParent?: number
    ) {}
}
