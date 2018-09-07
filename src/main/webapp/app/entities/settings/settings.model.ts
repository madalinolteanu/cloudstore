import { Font } from '../font/font.model';
import { Theme } from '../theme/theme.model';
import { Language } from '../language/language.model';
export class Settings {
    constructor(
        public userCode: string,
        public language: Language,
        public theme: Theme,
        public dateFormat: string,
        public fontType: Font
    ) {}
}
