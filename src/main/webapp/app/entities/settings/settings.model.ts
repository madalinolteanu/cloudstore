import { Font } from '../font/font.model';
import { Theme } from '../theme/theme.model';
import { Language } from '../language/language.model';
import {BaseEntity} from "../../shared/models/base-entity";
export class Settings implements BaseEntity{
    constructor(
        public id?: number,
        public userCode?: string,
        public language?: string,
        public theme?: string,
        public dateFormat?: string,
        public fontType?: string
    ) {}
}
