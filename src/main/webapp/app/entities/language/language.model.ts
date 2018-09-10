import {BaseEntity} from "../../shared/models/base-entity";
export class Language implements BaseEntity{
    constructor(
        public id?: number,
        public languageCode?: string,
        public languageName?: string
    ){}
}
