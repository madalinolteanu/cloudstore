import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { registerRoute } from './register.route';
import { TranslateModule, TranslatePipe } from 'ng2-translate/ng2-translate';

const ENTITY_STATES = [...registerRoute];

@NgModule({
    imports: [CommonModule, FormsModule, RouterModule.forRoot(ENTITY_STATES, { useHash: true }), TranslateModule.forRoot()],
    declarations: [RegisterComponent],
    entryComponents: [RegisterComponent],
    providers: [RegisterComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RegisterModule {}
