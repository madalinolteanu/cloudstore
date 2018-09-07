import { BaseEntity } from './base-entity';
/**
 * Created by Madalin on 9/6/2018.
 */
export class TableData implements BaseEntity {
    constructor(
        public id?: number,
        public fileName?: string,
        public fileType?: string,
        public fileUrl?: string,
        public parentId?: number,
        public index?: number
    ) {}
}
