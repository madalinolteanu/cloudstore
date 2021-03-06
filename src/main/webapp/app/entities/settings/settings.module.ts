import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
import {SettingsComponent} from "./settings.component";
import { FormsModule } from '@angular/forms';
import {SettingsService} from "./settings.service";


@NgModule({
    imports: [CommonModule, FormsModule],
    declarations: [],
    entryComponents: [SettingsComponent],
    providers: [SettingsService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
})
export class SettingsModule {}
