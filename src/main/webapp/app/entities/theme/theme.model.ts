import {BaseEntity} from "../../shared/models/base-entity";
export class Theme implements BaseEntity{
    constructor(
        public id?: number,
        public themeCode?: string,
        public themeName?: string
    ){}
}
