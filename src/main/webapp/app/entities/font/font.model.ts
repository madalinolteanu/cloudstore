import {BaseEntity} from "../../shared/models/base-entity";
export class Font implements BaseEntity{
    constructor(
        public id?: number,
        public fontCode: string,
        public fontName: string
    ){}
}
