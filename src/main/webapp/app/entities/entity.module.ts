import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CloudStoreModule } from './cloudstore/cloudstore.module';
import { AccountModule } from './account/account.module';
import { RegisterModule } from './register/register.module';
import { LoginModule } from './login/login.module';
import { SettingsModule } from './settings/settings.module';
import { FontComponent } from './font/font.component';
import { ThemeComponent } from './theme/theme.component';
import { LanguageComponent } from './language/language.component';
import {CloudFileComponent} from './file/file.component';
import { FileModule } from './file/file.module';
import { ThemeModule } from './theme/theme.module';
import { LanguageModule } from './language/language.module';
import { DirectoryModule } from './directory/directory.module';
import { FontModule } from './font/font.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        AccountModule,
        CloudStoreModule,
        RegisterModule,
        LoginModule,
        FileModule,
        DirectoryModule,
        ThemeModule,
        FontModule,
        LanguageModule,
        SettingsModule,
    ],
    declarations: [FontComponent, ThemeComponent, LanguageComponent, CloudFileComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LicEntityModule {}
