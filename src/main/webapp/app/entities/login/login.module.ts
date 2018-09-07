import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { LoginService, LoginComponent, loginRoute } from './';

const ENTITY_STATES = [...loginRoute];

@NgModule({
    imports: [CommonModule, FormsModule, RouterModule.forRoot(ENTITY_STATES, { useHash: true })],
    declarations: [LoginComponent],
    entryComponents: [LoginComponent],
    providers: [LoginService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LoginModule {}
